import { useEffect, useState } from "react";
import { useSendInvitation } from "../hooks/useSendInvitation";
import { MenuItem, Button, TextField, Typography } from "@mui/material";
// import { getVacancyByManager } from "../hooks/useGetVacancyByManager";

const SendInvitation = ({ data }) => {
  console.log("data", data);

  const { mutate } = useSendInvitation();
  const [subject, setSubject] = useState("");
  const [content, setContent] = useState("");
  const [vacancyId, setVacancyId] = useState("");
  const [vacancies, setVacancies] = useState([]);

  const [warning, setWarning] = useState(false);

  // Get vacancies available for logged-in manager
  const fetchVacanciesForManager = async () => {
    try {
      // const managerVacancies = await getVacancyByManager();
      // setVacancies(managerVacancies);
    } catch (error) {
      console.error("Error fetching vacancies for manager:", error);
    }
  };

  useEffect(() => {
    fetchVacanciesForManager();
  }, []);

  const handleSubmit = async (event) => {
    event.preventDefault();

    let selectedCandidateIds = [];

    if (data) {
      selectedCandidateIds = Object.keys(data).filter((id) => data[id]);

      console.log("Selected candidate IDs:", selectedCandidateIds);
    } else {
      return;
    }

    for (let i = 0; i < selectedCandidateIds.length; i++) {
      const candidateId = selectedCandidateIds[i];

      let invitationData = {
        candidateId,
        subject,
        content,
        vacancyId,
      };

      console.log("Sending invitation to candidate:", candidateId);
      console.log("submit", invitationData);

      try {
        await mutate(invitationData);
      } catch (error) {
        console.error("Error sending invitation:", error);
      }
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
              {vacancy.name} - {vacancy.jobFamilyName}
            </MenuItem>
          ))}
        </TextField>
        <Button type="submit" variant="contained" color="primary" mt={2}>
          Send
        </Button>
        {warning && (
          <Typography color="error" variant="body2" mt={2}>
            Please select at least one candidate before sending.
          </Typography>
        )}
      </form>
    </>
  );
};

export default SendInvitation;
