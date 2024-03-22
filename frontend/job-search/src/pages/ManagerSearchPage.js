import React from 'react';
import CandidatesFilter from '../components/ManagerSearch';
import CandidatesTable from '../components/CandidatesTable';
import {useState} from 'react';
import CardContainer from '../components/CardContainer';
import { useEffect } from 'react';
import SendInvitation from '../components/SendInvitation';
import InvitationModal from '../components/InvitationModal';

const ManagerSearchPage = () => {

    const [data, setData] = useState([]);


    const [rowsSelected,setRowsSelected]= useState();

    const handleRowSelectionChange = (selectedRows) => {
        setRowsSelected(selectedRows);
    };

    useEffect(() => {
       
        console.log("row selected parent", rowsSelected)

        if (rowsSelected){
            const selectedIds = Object.keys(rowsSelected).filter(id => rowsSelected[id]);
    
            console.log("Selected candidate IDs:", selectedIds);
        }
        
        
    }, [rowsSelected]);

    


    return (
        <div>
            <h1>Manager Search</h1>
            <CandidatesFilter setData={setData} />
            <CardContainer width='xl'>
                <CandidatesTable dataFromQuery={data}
                 onRowSelectionChange={handleRowSelectionChange} 
                
                ></CandidatesTable>
                <InvitationModal data={rowsSelected}>
                    <SendInvitation data={rowsSelected} ></SendInvitation>   
                </InvitationModal>
            </CardContainer>
        </div>
    );
};

export default ManagerSearchPage;