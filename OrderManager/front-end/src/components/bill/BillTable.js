import React, { useState, useEffect } from 'react';
import { DataGrid } from '@mui/x-data-grid';

const DataTable = () => {
    const [rows, setRows] = useState([]);
    const [columns, setColumns] = useState([]);

    useEffect(() => {
        // Fetch data from localhost endpoint
        fetch('http://localhost:8080/bills/getAll')
            .then(response => response.json())
            .then(data => {
                // Set the retrieved data to the state
                setRows(data);

                // Extract column names from the first object in the data
                const firstRow = data[0];
                if (firstRow) {
                    const columnNames = Object.keys(firstRow);
                    const newColumns = columnNames.map(columnName => ({
                        field: columnName,
                        headerName: columnName.toUpperCase(),
                        width: 200,
                    }));
                    setColumns(newColumns);
                }
            })
            .catch(error => console.error('Error fetching data:', error));
    }, []); // Empty dependency array ensures useEffect runs only once

    return (
        <div className="data-table-container">
            <DataGrid
                rows={rows}
                columns={columns}
                initialState={{
                    pagination: {
                        paginationModel: { page: 0, pageSize: 7},
                    },
                }}
            />
        </div>
    );
};

export default DataTable;
