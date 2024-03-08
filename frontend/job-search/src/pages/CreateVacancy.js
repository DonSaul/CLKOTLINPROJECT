import { TextField } from "@mui/material";
import CardContainer from "../components/CardContainer"
import { useState } from "react";





export const CreateVacancy = () => {


    const [companyName, setCompanyName] = useState();
    const [vacancyName, setVacancyName] = useState();
    const [description, setDescription] = useState();
    const [salaryExpectation, setSalaryExpectation] = useState();
    const [yearsOfExperience, setYearsOfExperience] = useState();
    const [jobFamily, setJobFamily] = useState('');


    const handleSubmit = (data) => {

        console.log("submit", data);
    }


    return (

        <>
            <CardContainer>
                Create vacancy

                <form onSubmit={handleSubmit}>


                    <TextField
                        label="Company name"
                        type="text"
                        value={null}
                        onChange={(e) => setCompanyName(e.target.value)}
                        fullWidth
                        margin="normal"
                        required
                    />

                    <TextField
                        label="Company name"
                        type="text"
                        value={null}
                        onChange={(e) => setVacancyName(e.target.value)}
                        fullWidth
                        margin="normal"
                        required
                    />

                    <TextField
                        id="outlined-multiline-static"
                        label="Multiline"
                        multiline
                        rows={4}
                        defaultValue="Default Value"
                    />




                </form>



            </CardContainer>



        </>
    )
}