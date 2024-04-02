import CardContainer from "../components/CardContainer";
import RegisterForm from "../components/RegisterForm";

export const CreateUser = () => {
  return (
    <>
      <CardContainer width="xs">
        <RegisterForm title="Create a User" variant="create"></RegisterForm>
      </CardContainer>
    </>
  );
};
