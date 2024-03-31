import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { getVacancyByManager } from "../hooks/useGetVacancyByManager";
import { useSendInvitation } from "../hooks/useSendInvitation";
import { MenuItem, Button, TextField } from "@mui/material";

export const PersonalInvitation = ({data}) => {
    console.log("data", data)
    const { id } = useParams();
    const { mutate } = useSendInvitation();
    const [subject, setSubject] = useState('');
    const [content, setContent] = useState('');
    const [vacancyId, setVacancyId] = useState();
    const [vacancies, setVacancies] = useState([])
    console.log("mutate", mutate);

   // Get vacancies available for logged-in manager
    const fetchVacanciesForManager = async () => {
        try {
            const managerVacancies = await getVacancyByManager();
            setVacancies(managerVacancies);
        } catch (error) {
            console.error('Error fetching vacancies for manager:', error);
        }
    };

    useEffect(() => {
        fetchVacanciesForManager();
    }, []);

    const handleSubmit = async (event) => {
        event.preventDefault();

        let invitationData =
        {
            candidateId: id,
            subject,
            content,
            vacancyId,
        };

        console.log("submit", invitationData);

        try {
            await mutate(invitationData);
        } catch (error) {
            console.error("Error sending invitation:", error);
        }
    };

    return (
        <>
            <form onSubmit={handleSubmit}>
                <TextField
                    id="outlined-multiline-static"
                    label="Subject"
                    value={subject}
                    onChange={(e) => setSubject(e.target.value)}
                    fullWidth
                    margin="normal"
                    inputProps={{ maxLength: 100 }}
                    required
                />

                <TextField
                    id="outlined-multiline-static"
                    label="Content"
                    value={content}
                    onChange={(e) => setContent(e.target.value)}
                    multiline
                    fullWidth
                    margin="normal"
                    rows={4}
                    inputProps={{ maxLength: 100 }}
                    required
                />

                <TextField
                    select
                    label="Select Vacancy"
                    value={vacancyId}
                    onChange={(e) => setVacancyId(e.target.value)}
                    fullWidth
                    margin="normal"
                    required
                >
                    {vacancies.map((vacancy) => (
                        <MenuItem key={vacancy.id} value={vacancy.id}>
                            {vacancy.name} - {vacancy.jobFamily.name}
                        </MenuItem>
                    ))}
                </TextField> 
                
                <Button type="submit" variant="contained" color="primary" >
                    Send
                </Button>
            </form>
        </>
    )
}

