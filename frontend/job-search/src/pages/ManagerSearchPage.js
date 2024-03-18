import React from 'react';
import CandidatesFilter from '../components/ManagerSearch';
import CandidatesTable from '../components/CandidatesTable';
import {useState} from 'react';
import CardContainer from '../components/CardContainer';

const ManagerSearchPage = () => {

    const [data, setData] = useState([]);
    return (
        <div>
            <h1>Advanced candidate search</h1>
            <CandidatesFilter setData={setData} />

            <CardContainer>
                <CandidatesTable dataFromQuery={data}></CandidatesTable>
            </CardContainer>
        </div>
    );
};

export default ManagerSearchPage;