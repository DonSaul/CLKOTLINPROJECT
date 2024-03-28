import React, { useState, useEffect } from 'react';
import CardContainer from '../components/CardContainer';
import { useParams, useNavigate } from 'react-router-dom';
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
import ManagerSearchPage from './ManagerSearchPage';
import DeleteConfirmationModal from '../components/DeleteConfirmationModal';
import LoadingSpinner from '../components/LoadingSpinner';

const VacancyView = () => {
    const { id } = useParams();
    const { getUserRole, getUserEmail } = useAuth();
    const { mutate: applyToVacancy, isError: isErrorApply, isSuccess: isSuccessApply } = useApplyVacancy();
    const { data: vacancyData, isLoading: isLoadingVacancy, isError: isErrorVacancy } = useGetVacancyById(id);
    const [candidates, setCandidates] = useState([]);
    const { mutate: deleteVacancy } = useDeleteVacancy();
    const [openDeleteModal, setOpenDeleteModal] = useState(false);
    const navigate = useNavigate();
    const appliedMessage = "Applied"
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
        const buttonDiv = document.getElementById(vacancyData.id);
        buttonDiv.innerHTML = appliedMessage;
    };

    const handleUpdate = () => {
        navigate(`${paths.vacancies}/update/${vacancyData.id}`)
    };

    const handleOpenDeleteModal = () => {
        setOpenDeleteModal(true);
    };

    const handleCloseDeleteModal = () => {
        setOpenDeleteModal(false);
    };

    const handleConfirmDelete = () => {
        deleteVacancy(id);
        handleCloseDeleteModal();
        navigate(paths.vacancies);
    };
    return (
        <>
            <CardContainer width='xl'>

                {isLoadingVacancy ?
                    (<>

                        <LoadingSpinner></LoadingSpinner>
                    </>)

                    :


                    (<>
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
                                            <div id={vacancyData.id}>
                                                {vacancyData.isApplied
                                                    ? appliedMessage
                                                    : (<Button variant="contained" color="primary" size="large" onClick={handleApply}>
                                                        Apply
                                                    </Button>)}
                                            </div>
                                        )}
                                        {vacancyData.manager.email === getUserEmail() && (
                                            <>
                                                <Button variant="contained" color="warning" size="large" onClick={handleOpenDeleteModal}>
                                                    Delete Vacancy
                                                </Button>

                                                <Button variant="contained" color="warning" size="large" onClick={handleUpdate}>
                                                    Update Vacancy
                                                </Button>
                                            </>
                                        )}
                                        {<DeleteConfirmationModal
                                            open={openDeleteModal}
                                            onClose={handleCloseDeleteModal}
                                            onConfirm={handleConfirmDelete}
                                        />
                                        }
                                    </CardActions>

                                </CardContent>
                                {vacancyData.manager.email === getUserEmail()
                                    ? (<CardContent style={{ padding: '20px', background: '#f5f5f5' }}> 
                                    <div style={{ marginBottom: '20px' }}>
                                        <Typography variant="h5" style={{ marginBottom: '10px' }}>Candidates that applied</Typography>
                                        <CandidatesTable dataFromQuery={candidates} fromVacancyView={true} />
                                    </div>
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
                    </>)}





            </CardContainer>

        </>
    );
};

export default VacancyView;
