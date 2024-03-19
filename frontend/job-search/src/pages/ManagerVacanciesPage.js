import React, { useState, useEffect } from 'react';
import VacancyTable from '../components/VacancyTable';
import CardContainer from '../components/CardContainer';
import { getVacancyByManager } from '../hooks/useGetVacancyByManager';

const ManagerVacanciesPage = () => {
    const [data, setData] = useState(null);
    
    const fetchData = async () => {
        try {
            const result = await getVacancyByManager();
            console.log("RESULT: ", result)
            setData(result);
        } catch (error) {
            console.error('Error fetching vacancies:', error);
        }
    };

    useEffect(() => {
        fetchData();
    }, []);

    return (
        <div>
            <h1>My Vacancies</h1>
            <CardContainer>
                {data ? <VacancyTable dataFromQuery={data} /> : <p>No vacancies found</p>}
            </CardContainer>
        </div>
    );
};

export default ManagerVacanciesPage;
