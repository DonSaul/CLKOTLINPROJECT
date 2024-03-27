import { MenuItem } from '@mui/material';
import Button from '@mui/material/Button';
import { MaterialReactTable, useMaterialReactTable } from 'material-react-table';
import { useEffect, useMemo, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ROLES } from '../helpers/constants';
import { useAuth } from '../helpers/userContext';
import { useApplyVacancy } from '../hooks/useApplyVacancy';
import { paths } from '../router/paths';
export default function VacancyTable({ dataFromQuery }) {


    const { getUserRole } = useAuth();
    const { mutate: applyToVacancy, isError, isSuccess } = useApplyVacancy();

    const navigate = useNavigate();

    const columnsVacancies = useMemo(() => {
        const columns = [
            {
                accessorKey: 'companyName',
                header: 'Company',
            },
            {
                accessorKey: 'jobFamily.name',
                header: 'Category',
            },
            {
                accessorKey: 'name',
                header: 'Name',
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
                accessorKey: 'vacancyId',
                header: 'ID',
                hidden: true,
            },
        ];

        if (getUserRole() === ROLES.CANDIDATE) {
            columns.push({
                id: 'applyButton',
                header: 'Status',
                Cell: ({ row }) => (
                    row.original.isApplied
                    ? appliedMessage
                    :
                    <div id={row.original.id}>
                        <Button variant="contained" color="primary" onClick={() => handleApply(row.original)}>
                        Apply
                    </Button>
                    </div>
                    
                )
            });
        }

        return columns;
    }, []);


    //optionally, you can manage any/all of the table state yourself
    const [rowSelection, setRowSelection] = useState({});

    useEffect(() => {
        console.log("rowSelection", rowSelection);
    }, [rowSelection]);

    const handleApply = (rowData) => {
        console.log('Applying to vacancy:', rowData);
        let applicationData =
        {
            vacancyId:rowData.id,
        }
        applyToVacancy(applicationData);
        const buttonDiv = document.getElementById(rowData.id);
        buttonDiv.innerHTML = appliedMessage;
    };
    const appliedMessage = "Applied"

    const table = useMaterialReactTable({
        columns: columnsVacancies,
        data: dataFromQuery ? dataFromQuery : [],
        hiddenColumns: ['id'],
        enableGlobalFilter: false,
        enableColumnFilters: false,
        //enableColumnOrdering: true, //enable some features
        enableRowSelection: false,
        enableHiding: false,
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
            <MenuItem key="edit" onClick={() => console.info('Delete')}>
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