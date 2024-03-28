import { TextField, Typography } from "@mui/material";
import CardContainer from "../components/CardContainer";
import { useState } from "react";
import useJobFamily from "../hooks/useJobFamily";
import { Autocomplete } from "@mui/material";
import { useCreateVacancy } from "../hooks/useCreateVacancy";
import { Button } from "@mui/material";
import WorkIcon from '@mui/icons-material/Work';
import EmojiPeopleIcon from '@mui/icons-material/EmojiPeople';
import DescriptionIcon from '@mui/icons-material/Description';
import MonetizationOnIcon from '@mui/icons-material/MonetizationOn';
import BusinessIcon from '@mui/icons-material/Business';
import BusinessCenterIcon from '@mui/icons-material/BusinessCenter';
import { useNavigate } from "react-router-dom";
import { paths } from "../router/paths";

export const CreateVacancy = () => {
    const { mutate } = useCreateVacancy();
    const { data: jobFamilies } = useJobFamily();
    const [companyName, setCompanyName] = useState('');
    const [vacancyName, setVacancyName] = useState('');
    const [description, setDescription] = useState('');
    const [salaryExpectation, setSalaryExpectation] = useState('');
    const [yearsOfExperience, setYearsOfExperience] = useState('');
    const [jobFamily, setJobFamily] = useState('');
    const navigate=useNavigate();
    const handleJobFamilyChange = (newValue) => {
        setJobFamily(newValue ? newValue.id : '');
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        let vacancyData = {
            companyName,
            name: vacancyName,
            description,
            salaryExpectation,
            yearsOfExperience,
            jobFamilyId: jobFamily
        };

        try {
            await mutate(vacancyData);
            navigate(paths.managerVacanciesPage);
            
        } catch {
            // Handle error
        }
    };

    return (
        <CardContainer>
            <Typography variant="h4" align="center" gutterBottom>
                <EmojiPeopleIcon fontSize="large" /> Create Vacancy <WorkIcon fontSize="large" />
            </Typography>
            <form onSubmit={handleSubmit}>
                <TextField
                    label={<><BusinessIcon /> Company Name</>}
                    type="text"
                    value={companyName}
                    onChange={(e) => setCompanyName(e.target.value)}
                    fullWidth
                    margin="normal"
                    required
                    sx={{ width: '65%', margin: 'auto', marginBottom: '16px' }}
                />
                <TextField
                    label={<><BusinessCenterIcon /> Vacancy Name</>}
                    type="text"
                    value={vacancyName}
                    onChange={(e) => setVacancyName(e.target.value)}
                    fullWidth
                    margin="normal"
                    required
                    sx={{ width: '65%', margin: 'auto', marginBottom: '16px' }}
                />
                <TextField
                    id="outlined-multiline-static"
                    label={<><DescriptionIcon /> Description</>}
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                    multiline
                    fullWidth
                    rows={4}
                    defaultValue=""
                    sx={{ width: '65%', margin: 'auto', marginBottom: '16px' }}
                />
                <TextField
                    label={<><WorkIcon /> Years of Experience</>}
                    type="number"
                    value={yearsOfExperience}
                    onChange={(e) => setYearsOfExperience(e.target.value)}
                    fullWidth
                    margin="normal"
                    required
                    sx={{ width: '65%', margin: 'auto', marginBottom: '16px' }}
                />
                <TextField
                    label={<><MonetizationOnIcon /> Salary Expectations</>}
                    type="number"
                    value={salaryExpectation}
                    onChange={(e) => setSalaryExpectation(e.target.value)}
                    fullWidth
                    margin="normal"
                    required
                    sx={{ width: '65%', margin: 'auto', marginBottom: '16px' }}
                />
                <Autocomplete
                    options={jobFamilies || []}
                    getOptionLabel={(option) => option.name || ''}
                    value={jobFamilies?.find((job) => job.id === jobFamily) || null}
                    isOptionEqualToValue={(option, value) => option.id === value?.id}
                    onChange={(e, newValue) => handleJobFamilyChange(newValue)}
                    renderInput={(params) => (
                        <TextField
                            {...params}
                            label={<><WorkIcon /> Select Job Family</>}
                            margin="normal"
                            fullWidth
                            sx={{ width: '65%', margin: 'auto', marginBottom: '16px' }}
                        />
                    )}
                />
                <Button type="submit" variant="contained" color="primary">
                    Create Vacancy
                </Button>
            </form>
        </CardContainer>
    );
};
