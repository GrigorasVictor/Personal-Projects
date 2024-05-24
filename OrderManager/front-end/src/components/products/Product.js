import React from 'react';
import SimpleBottomNavigation from "./BottomNavigation";
import DataTable from "./ProductTable";
import { useTheme } from '@mui/material/styles';

function Product() {
    const theme = useTheme();

    return (
        <div style={{ display: 'flex', flexDirection: 'column', position: 'relative', overflowY: 'auto' }}>
            <div style={{ flex: 1 }}>
                <h2 style={{ backgroundColor: theme.palette.primary.main, color: '#fff', padding: '10px 20px' }}>PRODUCTS</h2>
                <div>
                    <DataTable/>
                </div>
            </div>
            <div style={{ position: 'fixed', bottom: 0, left: '50%', transform: 'translateX(-50%)', paddingBottom: '5px' }}>
                <SimpleBottomNavigation/>
            </div>
        </div>
    );
}

export default Product;
