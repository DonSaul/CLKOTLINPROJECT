import React, { useState, useEffect } from 'react';
import { useParams, Navigate } from 'react-router-dom';
import { useGetVacancyById } from '../hooks/useGetVacancy';
import { useUpdateVacancy } from '../hooks/useUpdateVacancy';
import useJobFamily from '../hooks/useJobFamily'; 
import { TextField, Typography } from "@mui/material";
import CardContainer from "../components/CardContainer";
import { Autocomplete } from "@mui/material";
import { Button } from "@mui/material";
import WorkIcon from '@mui/icons-material/Work';
import DescriptionIcon from '@mui/icons-material/Description';
import MonetizationOnIcon from '@mui/icons-material/MonetizationOn';
import BusinessIcon from '@mui/icons-material/Business';
import BusinessCenterIcon from '@mui/icons-material/BusinessCenter';
import UpdateIcon from '@mui/icons-material/Update';

export const UpdateVacancy = () => {
    const { id } = useParams();
    const { data: vacancy, isLoading } = useGetVacancyById(id);
    const { data: jobFamilies } = useJobFamily();
    const { mutate } = useUpdateVacancy();
    const [vacancyId, setVacancyId] = useState('');
    const [companyName, setCompanyName] = useState('');
    const [vacancyName, setVacancyName] = useState('');
    const [description, setDescription] = useState('');
    const [salaryExpectation, setSalaryExpectation] = useState('');
    const [yearsOfExperience, setYearsOfExperience] = useState('');
    const [jobFamily, setJobFamily] = useState('');
    const [redirect, setRedirect] = useState(false);

    useEffect(() => {
        if (vacancy) {
            setVacancyId(vacancy.id);
            setCompanyName(vacancy.companyName);
            setVacancyName(vacancy.name);
            setDescription(vacancy.description);
            setSalaryExpectation(vacancy.salaryExpectation);
            setYearsOfExperience(vacancy.yearsOfExperience);
            setJobFamily(vacancy.jobFamily.id);
        }
    }, [vacancy]);

    const handleJobFamilyChange = (newValue) => {
        setJobFamily(newValue ? newValue.id : '');
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        let vacancyData = {
            id: id,
            companyName,
            name: vacancyName,
            description,
            salaryExpectation,
            yearsOfExperience,
            jobFamilyId: jobFamily
        };

        try {
            await mutate(vacancyData);
            setRedirect(true); 
        } catch {
            // Handle error
        }
    };

    if (redirect && vacancy) {
        return <Navigate to={`/vacancies/${vacancy.id}`} />; 
    }
    
    return (
        <CardContainer>
            <Typography variant="h4" align="center" gutterBottom>
                <UpdateIcon fontSize="large" /> Update Vacancy 
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
                    Update Vacancy
                </Button>
            </form>
        </CardContainer>
    );
};
