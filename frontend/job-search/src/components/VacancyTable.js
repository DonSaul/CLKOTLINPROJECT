import { useMemo, useState, useEffect } from 'react';
import { MaterialReactTable, useMaterialReactTable } from 'material-react-table';
import Button from '@mui/material/Button';
const data = [
    {
      name: 'John',
      age: 30,
    },
    {
      name: 'Sara',
      age: 25,
    },
  ]

const vacancyData = [
{
    jobFamily: 'Cybersecurity',
    yearsOfExperience: 3,
    salary: 3000000,
    vacancyId:1
},
{
    jobFamily: 'Backend development',
    yearsOfExperience: 5,
    salary: 5000000,
    vacancyId:2
},
{
    jobFamily: 'Frontend development',
    yearsOfExperience: 5,
    salary: 123456,
    vacancyId:3
},
{
    jobFamily: 'Backend development',
    yearsOfExperience: 99,
    salary: 7891234,
    vacancyId:4
},
{
    jobFamily: 'Backend development',
    yearsOfExperience: 50,
    salary: 98765543,
    vacancyId:5
    
},
{
    jobFamily: 'Backend development',
    yearsOfExperience: 100,
    salary: 98765543,
    vacancyId:6
    
},
]

export default function VacancyTable({dataFromQuery}) {
    const columns = useMemo(
        () => [
        {
            accessorKey: 'name', //simple recommended way to define a column
            header: 'Name',
            muiTableHeadCellProps: { sx: { color: 'green' } }, //optional custom props
            Cell: ({ cell }) => <span>{cell.getValue()}</span>, //optional custom cell render
        },
        {
            accessorFn: (row) => row.age, //alternate way
            id: 'age', //id required if you use accessorFn instead of accessorKey
            header: 'Age',
            Header: () => <i>Age</i>, //optional custom header render
        },
        ],
        [],
    );

    const columnsVacancies = useMemo(
        () => [
        {
            accessorKey: 'jobFamilyName',
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
        {
            id: 'applyButton', 
            header: 'Status',
            Cell: ({ row }) => (
                <Button variant="contained" color="primary" onClick={() => handleApply(row.original)}>
                    Apply
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
      };

    const table = useMaterialReactTable({
        columns:columnsVacancies,
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
    });

    const someEventHandler = () => {
        //read the table state during an event from the table instance
        console.log(table.getState().sorting);
    }

    return (
        <MaterialReactTable table={table} /> //other more lightweight MRT sub components also available
    );
}