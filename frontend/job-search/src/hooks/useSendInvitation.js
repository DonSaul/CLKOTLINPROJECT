import { useMutation } from "react-query";
import { toast } from "react-toastify";
import { AUTH_TOKEN_NAME } from "../helpers/constants"
import { ENDPOINTS } from "../helpers/endpoints";

const createInvitation = async (data) => {
    let token = localStorage.getItem(AUTH_TOKEN_NAME);

    const res = await fetch(ENDPOINTS.invitation, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
        },
        body: JSON.stringify(data)
    });
    return res;
};

export const useSendInvitation = () => {
    return useMutation(createInvitation, {
        onSuccess: (res) => {
            console.log("onSuccess res:", res);

            if (res.status===403) {
                toast.error('You are not allowed to do that');
            } else {
                toast.success("Invitation sent successfully!");
            }
        },

        onMutate: async (data) => {
            console.log("onMutate data:", data);
        },

        onError: (_err, data, context) => {
            toast.error("Error saving invitation!")
            console.log("Error on mutation", _err);
            console.log("Error data:", data);
        },    
    });
};