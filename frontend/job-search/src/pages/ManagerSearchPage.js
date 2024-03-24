import React, { useEffect, useState } from 'react';
import CandidatesTable from '../components/CandidatesTable';
import CardContainer from '../components/CardContainer';
import InvitationModal from '../components/InvitationModal';
import CandidatesFilter from '../components/ManagerSearch';
import SendInvitation from '../components/SendInvitation';

const ManagerSearchPage = () => {

    const [data, setData] = useState([]);
    const [rowsSelected, setRowsSelected] = useState();
    const handleRowSelectionChange = (selectedRows) => {
        setRowsSelected(selectedRows);
    };
    useEffect(() => {
        console.log("row selected parent", rowsSelected)
        if (rowsSelected) {
            const selectedIds = Object.keys(rowsSelected).filter(id => rowsSelected[id]);
            console.log("Selected candidate IDs:", selectedIds);
        }

    }, [rowsSelected]);

    return (
        <div>
            <h1>Advanced candidate search</h1>
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