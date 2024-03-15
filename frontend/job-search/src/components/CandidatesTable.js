import { useMemo, useState, useEffect } from 'react';
import { MaterialReactTable, useMaterialReactTable } from 'material-react-table';
import Button from '@mui/material/Button';
import { useApplyVacancy } from '../hooks/useApplyVacancy';
import { useGetCurrentUserCv } from '../hooks/useCV';
import { useNavigate } from 'react-router-dom';
import { ROLES } from '../helpers/constants';
import { useAuth } from '../helpers/userContext';
import { MenuItem } from '@mui/material';
import { paths } from '../router/paths';
export default function CandidatesTable({dataFromQuery}) {


    const {getUserRole} = useAuth();
    const {mutate:applyToVacancy, isError, isSuccess}=useApplyVacancy();  //remove

    const navigate = useNavigate();

    const columnsCandidates = useMemo(
        () => [
        {
            accessorKey: 'jobFamilyName',
            header: 'Category',
        },
        {
            accessorKey: 'firstName',
            header: 'Name',
        },
        {
            accessorKey: 'lastName',
            header: 'LastName',
        },
        {
            accessorKey: 'email',
            header: 'Email',
        },
        {
            accessorKey: 'yearsOfExperience',
            header: 'Years of experience',

        },
        {
            accessorKey: 'salaryExpectation', 
            header: 'Salary',
        },

        {
            id: 'applyButton', 
            header: 'Status',
            Cell: ({ row }) => (
                <Button variant="contained" color="primary" onClick={() => handleApply(row.original)} disabled={getUserRole()!==ROLES.CANDIDATE}>
                    Invite
                </Button>
            ),
          },
        ],
        [],
    );

    //optionally, you can manage any/all of the table state yourself
    const [rowSelection, setRowSelection] = useState({});

    useEffect(() => {
        //do something when the row selection changes
    }, [rowSelection]);

    const handleApply = (rowData) => {
        
        console.log('Applying to vacancy:', rowData);


        let applicationData=
        {
            vacancyId:rowData.id,
            


        }


        applyToVacancy(applicationData);



      };

    const table = useMaterialReactTable({
        columns:columnsCandidates,
        data:dataFromQuery?  dataFromQuery: [],
        hiddenColumns:['id'],
        enableGlobalFilter: false,
        enableColumnFilters:false,
        //enableColumnOrdering: true, //enable some features
        enableRowSelection: false,
        enableHiding:false,
        enablePagination: true, //disable a default feature
        onRowSelectionChange: setRowSelection, //hoist internal state to your own state (optional)
        state: { rowSelection }, //manage your own state, pass it back to the table (optional)
        initialState: { columnVisibility: { vacancyId: false } },
        //enableHiding:false
        enableRowActions: true,
  renderRowActionMenuItems: ({ row }) => [
    <MenuItem key="edit" onClick={() => {
        console.log("row",row);
        navigate(`${paths.vacancies}/${row.original.id}`);
        }}>
      Visit 
    </MenuItem>,
    <MenuItem key="delete" onClick={() => console.info('Delete')}>
      Delete
    </MenuItem>,
  ],
        
    });

    const someEventHandler = () => {
        //read the table state during an event from the table instance
        console.log(table.getState().sorting);
    }

    return (
        <MaterialReactTable table={table} /> //other more lightweight MRT sub components also available
    );
}