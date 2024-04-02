import { MenuItem } from "@mui/material";
import Button from "@mui/material/Button";
import {
  MaterialReactTable,
  useMaterialReactTable,
} from "material-react-table";
import { useEffect, useMemo, useState } from "react";
import { useNavigate } from "react-router-dom";
import { ROLES } from "../helpers/constants";
import { useAuth } from "../helpers/userContext";
import { useApplyVacancy } from "../hooks/useApplyVacancy";
import { paths } from "../router/paths";
import { useDeleteVacancy } from "../hooks/useDeleteVacancy";
import DeleteConfirmationModal from "./DeleteConfirmationModal";
import { toast } from "react-toastify";
import VisibilityIcon from "@mui/icons-material/Visibility";

export default function VacancyTable({ dataFromQuery }) {
  const { getUserRole, getUserIdFromToken } = useAuth();
  const { mutate: applyToVacancy, isError, isSuccess } = useApplyVacancy();
  const [openDeleteModal, setOpenDeleteModal] = useState(false);
  const { mutate: deleteVacancy } = useDeleteVacancy();
  const [selectedId, setSelectedId] = useState(null);
  const [vacancies, setVacancies] = useState(dataFromQuery);

  const navigate = useNavigate();

  useEffect(() => {
    setVacancies(dataFromQuery);
  }, [dataFromQuery]);

  const columnsVacancies = useMemo(() => {
    const columns = [
      {
        accessorKey: "companyName",
        header: "Company",
      },
      {
        accessorKey: "jobFamily.name",
        header: "Category",
      },
      {
        accessorKey: "name",
        header: "Name",
      },
      {
        accessorKey: "yearsOfExperience",
        header: "Years of experience",
      },
      {
        accessorKey: "salaryExpectation",
        header: "Salary",
      },
      {
        accessorKey: "vacancyId",
        header: "ID",
        hidden: true,
      },
    ];

    if (getUserRole() === ROLES.CANDIDATE) {
      columns.unshift({
        accessorKey: "viewVacancy",
        header: "View Vacancy",
        Cell: ({ row }) => (
          <div style={{ display: "flex", justifyContent: "center" }}>
            <VisibilityIcon
              key="viewEye"
              onClick={() => navigate(`${paths.vacancies}/${row.original.id}`)}
              style={{ cursor: "pointer" }}
            />
          </div>
        ),
      });

      columns.push({
        id: "applyButton",
        header: "Status",
        Cell: ({ row }) =>
          row.original.isApplied ? (
            appliedMessage
          ) : (
            <div id={row.original.id}>
              <Button
                variant="contained"
                color="primary"
                onClick={() => handleApply(row.original)}
              >
                Apply
              </Button>
            </div>
          ),
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
    console.log("Applying to vacancy:", rowData);
    let applicationData = {
      vacancyId: rowData.id,
    };
    applyToVacancy(applicationData);
    const buttonDiv = document.getElementById(rowData.id);
    buttonDiv.innerHTML = appliedMessage;
  };
  const appliedMessage = "Applied";

  const handleOpenDeleteModal = () => {
    setOpenDeleteModal(true);
  };

  const handleCloseDeleteModal = () => {
    setOpenDeleteModal(false);
  };

  const handleConfirmDelete = async () => {
    try {
      await deleteVacancy(selectedId);
      setVacancies(vacancies.filter((vacancy) => vacancy.id !== selectedId));
      handleCloseDeleteModal();
    } catch (error) {
      toast.error("Error deleting vacancy:", error);
    }
  };

  const userRoleIsCandidate = () => getUserRole() === ROLES.CANDIDATE;

  const table = useMaterialReactTable({
    columns: columnsVacancies,
    data: vacancies,
    hiddenColumns: ["id"],
    enableGlobalFilter: false,
    enableColumnFilters: false,
    enableRowSelection: false,
    enableHiding: false,
    enablePagination: true,
    onRowSelectionChange: setRowSelection,
    state: { rowSelection },
    initialState: {
      columnVisibility: { vacancyId: false },
      density: "compact",
    },
    enableRowActions: !userRoleIsCandidate(),
    renderRowActionMenuItems: ({ row }) => {
      const deleteMenuItem =
        getUserRole() === ROLES.MANAGER
          ? getUserIdFromToken() === row.original.manager.id && (
              <MenuItem
                key="edit"
                onClick={() => {
                  setSelectedId(row.original.id);
                  console.log("selectedId", selectedId);
                  handleOpenDeleteModal();
                }}
              >
                Delete
              </MenuItem>
            )
          : null;

      return [
        <MenuItem
          key="view"
          onClick={() => {
            console.log("row", row);
            navigate(`${paths.vacancies}/${row.original.id}`);
          }}
        >
          View
        </MenuItem>,
        deleteMenuItem,
      ];
    },
  });

  const someEventHandler = () => {
    //read the table state during an event from the table instance
    console.log(table.getState().sorting);
  };

  return (
    <>
      <MaterialReactTable table={table} />
      {
        <DeleteConfirmationModal
          open={openDeleteModal}
          onClose={handleCloseDeleteModal}
          onConfirm={handleConfirmDelete}
        />
      }
    </>
  );
}
