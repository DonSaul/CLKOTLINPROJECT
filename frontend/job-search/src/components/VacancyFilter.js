import { Autocomplete, Button, Grid, TextField, Typography } from '@mui/material';
import React, { useEffect, useState } from 'react';
import useGetVacancies from '../hooks/useGetVacancies';
import useJobFamily from '../hooks/useJobFamily';
import CardContainer from './CardContainer';
import WorkIcon from '@mui/icons-material/Work';
import EmojiPeopleIcon from '@mui/icons-material/EmojiPeople';
import MonetizationOnIcon from '@mui/icons-material/MonetizationOn';
import BusinessCenterIcon from '@mui/icons-material/BusinessCenter';
import EmojiEventsTwoToneIcon from '@mui/icons-material/EmojiEventsTwoTone';
import { EmojiEventsTwoTone } from '@mui/icons-material';

export const VacancyFilter = ({ onFilterChange, setData }) => {
    const [yearsOfExperience, setYearsOfExperience] = useState('');
    const [jobFamily, setJobFamily] = useState('');
    const [salary, setSalary] = useState('');
    const { dataVacancies, refetch } = useGetVacancies(salary, jobFamily, yearsOfExperience);
    const { data: jobFamilies } = useJobFamily();

    const handleYearsOfExperienceChange = (event) => {
        setYearsOfExperience(event.target.value);
    };
    const handleJobFamilyChange = (newValue) => {
        setJobFamily(newValue ? newValue.id : '');
    };
    const handleSalaryChange = (event) => {
        setSalary(event.target.value);
    };

    //only for test
    useEffect(() => {
        const fetchData = async () => {
            try {
                const initialData = await refetch('', '', '');
                console.log("Initial data fetched,n o params:", initialData);
                setData(initialData.data);
            } catch (error) {
                console.error("Error fetching initial data:", error);
            }
        };

        fetchData();
    }, []);
    const handleApplyFilter = async () => {
        try {
            const fetchedData = await refetch(salary, jobFamily, yearsOfExperience);
            console.log("fetched:", fetchedData);
            setData(fetchedData.data);
        } catch (error) {

            console.error("Error fetching data:", error);
        }
    };
    return (
        <div>
            <CardContainer>
                <Typography variant="h6" align="center" gutterBottom>
                    <WorkIcon fontSize="large" /> Search Vacancies <EmojiPeopleIcon fontSize="large" />
                </Typography>
                <Grid container spacing={2} alignItems="center">
                    <Grid item xs={3}>
                        <TextField
                            label={<><EmojiEventsTwoToneIcon /> Years of Experience</>}

                            type="number"
                            value={yearsOfExperience}
                            onChange={handleYearsOfExperienceChange}
                            fullWidth
                            margin="normal"
                        />
                    </Grid>
                    <Grid item xs={3}>
                        <Autocomplete
                            options={jobFamilies || []}
                            getOptionLabel={(option) => option.name || ''}
                            value={jobFamilies?.find((job) => job.id === jobFamily) || null}
                            isOptionEqualToValue={(option, value) => option.id === value?.id}
                            onChange={(e, newValue) => handleJobFamilyChange(newValue)}
                            renderInput={(params) => <TextField {...params} label={<><BusinessCenterIcon /> Job Family</>} margin="normal" />}
                        />

                    </Grid>
                    <Grid item xs={3}>
                        <TextField
                            label={<><MonetizationOnIcon /> Salary</>}
                            type="number"
                            value={salary}
                            onChange={handleSalaryChange}
                            fullWidth
                            margin="normal"
                        />
                    </Grid>
                    <Grid item xs={3}>
                        <Button onClick={handleApplyFilter} variant="contained" color="primary">
                            Apply Filter
                        </Button>
                    </Grid>
                </Grid>
            </CardContainer>
        </div>
    );
};

export default VacancyFilter;
