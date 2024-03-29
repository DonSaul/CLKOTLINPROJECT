import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { getVacancyByManager } from "../hooks/useGetVacancyByManager";
import { useSendInvitation } from "../hooks/useSendInvitation";
import CardContainer from "../components/CardContainer";
import {
  Select,
  MenuItem,
  Autocomplete,
  Button,
  TextField,
  FormControl,
  InputLabel,
} from "@mui/material";

export const SendInvite = ({ data }) => {
  console.log("data", data);
  const { id } = useParams();
  const { mutate } = useSendInvitation();
  const [subject, setSubject] = useState("");
  const [content, setContent] = useState("");
  const [vacancyId, setVacancyId] = useState();
  const [vacancies, setVacancies] = useState([]);
  console.log("mutate", mutate);

  useEffect(() => {
    const fetchVacancies = async () => {
      try {
        const data = await getVacancyByManager();
        setVacancies(data);
      } catch (error) {
        console.error("Error fetching vacancies:", error);
      }
    };
    fetchVacancies();
  }, []);

  const handleSubmit = async (event) => {
    event.preventDefault();

    let invitationData = {
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
      <CardContainer>
        Send invitation
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

          <FormControl fullWidth margin="normal">
            <InputLabel id="demo-simple-select-helper-label">
              Vacancy
            </InputLabel>
            <Select
              value={vacancyId}
              onChange={(e) => setVacancyId(e.target.value)}
              displayEmpty
              required
            >
              <MenuItem value="" disabled>
                Select Vacancy
              </MenuItem>
              {vacancies.map((vacancy) => (
                <MenuItem key={vacancy.id} value={vacancy.id}>
                  {vacancy.companyName} - {vacancy.name} -{" "}
                  {vacancy.jobFamilyName}
                </MenuItem>
              ))}
            </Select>
          </FormControl>

          <Button type="submit" variant="contained" color="primary">
            Send
          </Button>
        </form>
      </CardContainer>
    </>
  );
};
