import React, { useMemo, useState, useEffect } from 'react';
import { MaterialReactTable, useMaterialReactTable } from 'material-react-table';
import Button from '@mui/material/Button';
import { MenuItem } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../helpers/userContext';
import { paths } from '../router/paths';
import { ListItemIcon } from '@mui/material';
import { IconButton } from '@mui/material';
import { Tooltip } from '@mui/material';
import Menu from '@mui/material/Menu';
import { Visibility } from '@mui/icons-material';
import { Box } from '@mui/material';
const StandardTable = ({ columns, data, actions, getUserRole }) => {
    const navigate = useNavigate();
    const [rowSelection, setRowSelection] = useState({});
    const [anchorEl, setAnchorEl] = useState(null); // State for menu anchor element

    useEffect(() => {

    }, [rowSelection]);



    const handleActionClick = (event) => {
        setAnchorEl(event.currentTarget); 
    };

    const handleActionClose = () => {
        setAnchorEl(null); 
    };

    const handleAction = (action, rowData) => {
        action(rowData);
        handleActionClose(); 
    };

    const hiddenColumns = useMemo(() => {
        return columns.reduce((acc, column) => {
            if (column.hidden) {
                acc.push(column.accessorKey);
            }
            return acc;
        }, []);
    }, [columns]);
    console.log("hidden",hiddenColumns)


    const table = useMaterialReactTable({
        columns: columns,
        data: data ? data : [],
        hiddenColumns:hiddenColumns,
        enableGlobalFilter: false,
        enableColumnFilters: false,
        enableRowSelection: false,
        enablePagination: true,
        onRowSelectionChange: setRowSelection,
        state: { rowSelection },
        positionActionsColumn: 'first',
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
          enableColumnResizing: true,
          columnResizeMode: 'onChange', //default
        enableRowActions: true, // Set enableRowActions to true
        renderRowActions : ({ row }) => {
            return [
                <>
                {actions && actions.map((action, index) => (
                <Tooltip key={index} title={action.label}>
                    <IconButton onClick={() => handleAction(action, row)}>
                        {action.icon} {/* Render the icon provided in the action */}
                    </IconButton>
                </Tooltip>
            ))}
            </>
              ];
            },
    });

    return <MaterialReactTable table={table} />;
};

export default StandardTable;
