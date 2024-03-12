import { TextField } from "@mui/material";
import CardContainer from "../components/CardContainer"
import { useState } from "react";
import useJobFamily from "../hooks/useJobFamily";
import { Autocomplete } from "@mui/material";
import { useCreateVacancy } from "../hooks/useCreateVacancy";
import {Button} from "@mui/material";


export const CreateVacancy = () => {

    const { mutate } = useCreateVacancy();
    const { data: jobFamilies } = useJobFamily();
    const [companyName, setCompanyName] = useState();
    const [vacancyName, setVacancyName] = useState();
    const [description, setDescription] = useState();
    const [salaryExpectation, setSalaryExpectation] = useState();
    const [yearsOfExperience, setYearsOfExperience] = useState();
    const [jobFamily, setJobFamily] = useState('');


    const handleJobFamilyChange = (newValue) => {
        setJobFamily(newValue ? newValue.id : '');
    };

    const handleSubmit =async (event) => {
        event.preventDefault()


        

        let vacancyData =
        {
            companyName,
            name:vacancyName,
            description,
            salaryExpectation,
            yearsOfExperience,
            jobFamilyId:jobFamily



        }
        console.log("submit", vacancyData);

        try{
            await mutate(vacancyData);
        } catch {

        }
       

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
                        label="Vacancy name"
                        type="text"
                        value={null}
                        onChange={(e) => setVacancyName(e.target.value)}
                        fullWidth
                        margin="normal"
                        required
                    />

                    <TextField

                        id="outlined-multiline-static"
                        label="Description"
                        value={null}
                        onChange={(e) => setDescription(e.target.value)}
                        multiline
                        fullWidth
                        rows={4}
                        defaultValue=""
                    />

                    <TextField
                        label="Years of Experience"
                        type="number"
                        value={yearsOfExperience}
                        onChange={(e) => setYearsOfExperience(e.target.value)}
                        fullWidth
                        margin="normal"
                        required
                    />

                    <TextField
                        label="Salary Expectations"
                        type="number"
                        value={salaryExpectation}
                        onChange={(e) => setSalaryExpectation(e.target.value)}
                        fullWidth
                        margin="normal"
                        required
                    />

                    <Autocomplete
                        options={jobFamilies || []}
                        getOptionLabel={(option) => option.name || ''}
                        value={jobFamilies?.find((job) => job.id === jobFamily) || null}
                        isOptionEqualToValue={(option, value) => option.id === value?.id}
                        onChange={(e, newValue) => handleJobFamilyChange(newValue)}
                        renderInput={(params) => <TextField {...params} label={`Select Job Family`} margin="normal" />}

                    />

                    <Button type="submit" variant="contained" color="primary" >
                        Create vacancy
                    </Button>




                </form>



            </CardContainer>



        </>
    )
}