import React, { useState, useEffect } from 'react';
import CardContainer from '../components/CardContainer';
import { useParams, useNavigate } from 'react-router-dom'; // Importa useNavigate en lugar de useHistory
import { useGetVacancyById } from '../hooks/useGetVacancy';
import { Button, Card, CardHeader, Typography } from '@mui/material';
import { CardContent } from '@mui/material';
import { Box } from '@mui/material';
import { Grid } from '@mui/material';
import { Stack } from '@mui/material';
import { CardActions } from '@mui/material';
import { useApplyVacancy } from '../hooks/useApplyVacancy';
import { useAuth } from '../helpers/userContext';
import { ROLES } from '../helpers/constants';
import CandidatesTable from '../components/CandidatesTable';
import { getCandidatesByApplication } from '../hooks/useGetCandidatesByApplication';
import { useDeleteVacancy } from '../hooks/useDeleteVacancy';
import { CreateVacancy } from './CreateVacancy';
import { paths } from '../router/paths';

const VacancyView = () => {
    const { id } = useParams();
    const { getUserRole, getUserEmail } = useAuth();
    const { mutate: applyToVacancy, isError: isErrorApply, isSuccess: isSuccessApply } = useApplyVacancy();
    const { data: vacancyData, isLoading: isLoadingVacancy, isError: isErrorVacancy } = useGetVacancyById(id);
    const [candidates, setCandidates] = useState([]);
    const { mutate: deleteVacancy } = useDeleteVacancy();
    const navigate = useNavigate();

    console.log('Vacancy Data:', vacancyData);

    const fetchCandidates = async () => {
        try {
            const result = await getCandidatesByApplication(id);
            setCandidates(result);
        } catch (error) {
            console.error('Error fetching vacancies:', error);
        }
    };

    useEffect(() => {
        fetchCandidates();
    }, []);

    const handleApply = (rowData) => {
        console.log('Applying to vacancy:', rowData);
        let applicationData = {
            vacancyId: id
        }
        applyToVacancy(applicationData);
    };

    const handleDelete = (rowData) => {
        console.log('Deleting vacancy:', rowData);
        deleteVacancy(id);
    };

    const handleUpdate = () => {
        navigate(`${paths.vacancies}/update/${vacancyData.id}`)
    };

    return (
        <>
            <CardContainer width='xl'>
                {vacancyData ? (
                    <>
                        <CardHeader title={vacancyData.name} subheader={vacancyData.companyName} />
                        <CardContent>
                            <Typography variant="subtitle1" style={{ fontSize: '1.2rem' }}>Job Description:</Typography>
                            <Box mb={2}>{vacancyData.description}</Box>

                            <Grid mb={4} container spacing={2} style={{ display: 'flex' }}>
                                <Grid item xs={4} style={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                                    <Stack direction="row" spacing={2}>
                                        <Stack>
                                            <b>Job Family </b>
                                        </Stack>
                                        <Stack>
                                            {vacancyData.jobFamily.name}
                                        </Stack>

                                    </Stack>
                                </Grid>
                                <Grid item xs={4} style={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                                    <Stack direction="row" spacing={2}>
                                        <Stack>
                                            <b>Salary Expectation </b>
                                        </Stack>
                                        <Stack>
                                            {vacancyData.salaryExpectation}
                                        </Stack>
                                    </Stack>
                                </Grid>
                                <Grid item xs={4} style={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                                    <Stack direction="row" spacing={2} >
                                        <Stack>
                                            <b>Years of Experience </b>
                                        </Stack>
                                        <Stack>
                                            {vacancyData.yearsOfExperience}
                                        </Stack>
                                    </Stack>
                                </Grid>
                            </Grid>
                            <CardActions style={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                                {getUserRole() === ROLES.CANDIDATE && (
                                    <Button variant="contained" color="primary" size="large" onClick={handleApply}>
                                        Apply
                                    </Button>
                                )}
                                {vacancyData.manager.email === getUserEmail() && (
                                    <>
                                        <Button variant="contained" color="warning" size="large" onClick={handleDelete}>
                                            Delete Vacancy
                                        </Button>
                                        <Button variant="contained" color="warning" size="large" onClick={handleUpdate}>
                                            Update Vacancy
                                        </Button>
                                    </>
                                )}
                            </CardActions>

                        </CardContent>
                        {vacancyData.manager.email === getUserEmail()
                            ? (<CardContent>
                                <Typography variant="subtitle1" style={{ fontSize: '1.2rem' }}>Candidates that applied</Typography>
                                <CandidatesTable dataFromQuery={candidates}></CandidatesTable>
                            </CardContent>)
                            : null
                        }

                    </>
                ) : (
                    <CardContent>
                        <Typography variant="h6" color="textSecondary" align="center">
                            No Vacancy Found
                        </Typography>
                    </CardContent>
                )}

            </CardContainer>

        </>
    );
};

export default VacancyView;
