import CardContainer from "../components/CardContainer";
import { PersonalInvitation } from "../components/PersonalInvitation";

const InvitationPage = () => {
    
    return (
        <div>
            <h3>Send Invitation</h3>
            <CardContainer>
                <PersonalInvitation />
            </CardContainer>
            
        </div>
    );
};

export default InvitationPage;