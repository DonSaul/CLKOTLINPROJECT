import React, { useState, useEffect } from 'react';
import VacancyTable from '../components/VacancyTable';
import CardContainer from '../components/CardContainer';
import { getVacancyByManager } from '../hooks/useGetVacancyByManager';
import { CircularProgress } from '@mui/material';
import LoadingSpinner from '../components/LoadingSpinner';

const ManagerVacanciesPage = () => {
    const [data, setData] = useState(null);
    const [isLoading, setIsLoading] = useState(true);

    const fetchData = async () => {
        setIsLoading(true);
        try {
            const result = await getVacancyByManager();
            console.log("RESULT: ", result)
            setData(result);
        } catch (error) {
            console.error('Error fetching vacancies:', error);
        } finally {
            setIsLoading(false);
        }
    };

    useEffect(() => {
        fetchData();
    }, []);

    return (
        <div>
            <h1>My Vacancies</h1>
            <CardContainer>
                {isLoading ? (
                    <LoadingSpinner></LoadingSpinner>
                ) : (
                    data ? <VacancyTable dataFromQuery={data} /> : <p>No vacancies found</p>
                )}
            </CardContainer>
        </div>
    );
};

export default ManagerVacanciesPage;
