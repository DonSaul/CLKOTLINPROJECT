import Button from "@mui/material/Button";
import {
  MaterialReactTable,
  useMaterialReactTable,
} from "material-react-table";
import { useEffect, useMemo, useState } from "react";
// import { useApplyVacancy } from '../hooks/useApplyVacancy';
import { MenuItem } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { ROLES } from "../helpers/constants";
import { useAuth } from "../helpers/userContext";
import { useSendInvitation } from "../hooks/useSendInvitation";
import { paths } from "../router/paths";
// import { PersonalInvitation } from './PersonalInvitation';
import VisibilityIcon from "@mui/icons-material/Visibility";

export default function CandidatesTable({
  dataFromQuery,
  onRowSelectionChange,
  fromVacancyView,
}) {
  const { getUserRole } = useAuth();
  const { mutate: sendInvitation, isError, isSuccess } = useSendInvitation();
  const navigate = useNavigate();
  const columnsCandidates = useMemo(
    () => [
      {
        accessorKey: "viewProfile",
        header: "View Profile",
        Cell: ({ row }) => (
          <div style={{ display: "flex", justifyContent: "center" }}>
            <VisibilityIcon
              key="viewEye"
              onClick={() => navigate(`${paths.profiles}/${row.original.id}`)}
              style={{ cursor: "pointer" }}
            />
          </div>
        ),
      },
      {
        accessorKey: "jobFamilies",
        header: "Experience areas",
        Cell: ({ row }) => (
          <div>
            {row.original.jobFamilies?.map((jobFamily) => (
              <div key={jobFamily.id}>{jobFamily.name}</div>
            ))}
          </div>
        ),
      },
      {
        accessorKey: "firstName",
        header: "Name",
      },
      {
        accessorKey: "lastName",
        header: "LastName",
      },
      {
        accessorKey: "email",
        header: "Email",
      },
      {
        accessorKey: "yearsOfExperience",
        header: "Years of experience",
      },
      {
        accessorKey: "salaryExpectation",
        header: "Salary Expectation",
      },
      {
        id: "sendButton",
        header: "Status",
        Cell: ({ row }) =>
          row.original.aplicationStatus ? (
            row.original.aplicationStatus
          ) : (
            <Button
              variant="contained"
              color="primary"
              onClick={() => handleInvite(row.original)}
              disabled={getUserRole() !== ROLES.MANAGER}
            >
              Invite
            </Button>
          ),
      },
    ],
    []
  );

  //optionally, you can manage any/all of the table state yourself
  const [rowSelection, setRowSelection] = useState({});

  const handleRowSelectionChange = (selectedRowIds) => {
    setRowSelection(selectedRowIds);
    onRowSelectionChange(selectedRowIds);
  };

  useEffect(() => {
    //do something when the row selection changes
  }, [rowSelection]);

  const handleInvite = (rowData) => {
    const candidateId = rowData.id;
    console.log("data in row", rowData);
    console.log("Sending invitation to candidate:", candidateId);
    navigate(`${paths.sendInvitation.replace(":id", candidateId)}`);
  };

  const table = useMaterialReactTable({
    columns: columnsCandidates,
    data: dataFromQuery ? dataFromQuery : [],
    hiddenColumns: ["id"],
    enableGlobalFilter: false,
    enableColumnFilters: false,
    //enableColumnOrdering: true, //enable some features
    enableRowSelection: fromVacancyView ? false : true,
    getRowId: (originalRow) => originalRow.id,
    //onRowSelectionChange: handleRowSelectionChange,
    enableHiding: false,
    enablePagination: true, //disable a default feature
    //onRowSelectionChange: setRowSelection, //hoist internal state to your own state (optional)
    onRowSelectionChange: handleRowSelectionChange,
    layoutMode: "grid", // Set the layout mode to 'grid'
    displayColumnDefOptions: {
      "mrt-row-actions": {
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
    initialState: {
      columnVisibility: { vacancyId: false },
      density: "compact",
    },
    //enableHiding:false
    enableRowActions: false,
  });

  const someEventHandler = () => {
    //read the table state during an event from the table instance
    // console.log(table.getState().sorting);
  };

  return (
    <MaterialReactTable table={table} /> //other more lightweight MRT sub components also available
  );
}
