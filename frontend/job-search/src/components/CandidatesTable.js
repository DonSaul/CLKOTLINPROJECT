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
export default function CandidatesTable({ dataFromQuery,onRowSelectionChange }) {

    const { getUserRole } = useAuth();
    const { mutate: applyToVacancy, isError, isSuccess } = useApplyVacancy();  //remove

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
                    <Button variant="contained" color="primary" onClick={() => handleApply(row.original)} disabled={getUserRole() !== ROLES.CANDIDATE}>
                        Invite
                    </Button>
                ),
            },
        ],
        [],
    );

    //optionally, you can manage any/all of the table state yourself
    const [rowSelection, setRowSelection] = useState({});

    const handleRowSelectionChange = (selectedRowIds) => {
        //console.log("selected", selectedRowIds)
        setRowSelection(selectedRowIds);
        onRowSelectionChange(selectedRowIds);
    };

    useEffect(() => {
        //do something when the row selection changes
       // console.log("row selected", rowSelection)
        
    }, [rowSelection]);

    const handleApply = (rowData) => {

        console.log('Applying to vacancy:', rowData);


        let applicationData =
        {
            vacancyId: rowData.id,



        }


        applyToVacancy(applicationData);



    };

    const table = useMaterialReactTable({
        columns: columnsCandidates,
        data: dataFromQuery ? dataFromQuery : [],
        hiddenColumns: ['id'],
        enableGlobalFilter: false,
        enableColumnFilters: false,
        //enableColumnOrdering: true, //enable some features
        enableRowSelection: true,
        getRowId: (originalRow) => originalRow.id,
        //onRowSelectionChange: handleRowSelectionChange,
        enableHiding: false,
        enablePagination: true, //disable a default feature
        //onRowSelectionChange: setRowSelection, //hoist internal state to your own state (optional)
        onRowSelectionChange: handleRowSelectionChange,
        layoutMode: "grid", // Set the layout mode to 'grid'
        displayColumnDefOptions: {
            'mrt-row-actions': {
                size: 50, // Set the size for row actions
                grow: false,
            },
        },
        defaultColumn: {
            maxSize: 400,
            minSize: 80,
            size: 150, //default size is usually 180
          },
        state: { rowSelection }, //manage your own state, pass it back to the table (optional)
        initialState: { columnVisibility: { vacancyId: false } },
        //enableHiding:false
        enableRowActions: true,
        positionActionsColumn: 'last',
        renderRowActionMenuItems: ({ row }) => [
            <MenuItem key="visit" onClick={() => {
                console.log("row", row);
                //navigate(`${paths.vacancies}/${row.original.id}`);
            }}>
                Visit
            </MenuItem>,
            <MenuItem key="invitation" >
            Send Invitation
            </MenuItem>
            
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