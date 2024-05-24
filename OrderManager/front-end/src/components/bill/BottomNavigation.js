import * as React from 'react';
import Box from '@mui/material/Box';
import BottomNavigation from '@mui/material/BottomNavigation';
import BottomNavigationAction from '@mui/material/BottomNavigationAction';
import AddIcon from '@mui/icons-material/Add';
import SearchIcon from '@mui/icons-material/Search';
import { Dialog, DialogContent, DialogTitle, TextField } from "@mui/material";
import Button from "@mui/material/Button";

export default function SimpleBottomNavigation() {
    const [value, setValue] = React.useState(0);
    const [open, setOpen] = React.useState(false);
    const [dialogTitle, setDialogTitle] = React.useState('');
    const [foundOrder, setFoundOrder] = React.useState(null); // State to hold found order data
    const [orderFound, setOrderFound] = React.useState(false); // Flag to indicate if order was found
    const [BillsId, setBillsId] = React.useState(0);
    const [Orders, setOrders] = React.useState({
        id: 0,
        client_id: '',
        product_id: '',
        quantity: '',
        order_date: '',
        status: 'Pending'
    });

    const handleAddClick = () => {
        setDialogTitle('Add Order');
        setOpen(true);
    };

    const handleFindClick = () => {
        setDialogTitle('Find by Id');
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleAddSubmit = (e) => {
        e.preventDefault();
        console.log('Add form submitted');
        fetch("http://localhost:8080/orders/add", {
            method: "POST",
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(Orders)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Internal server error');
                }
                alert('The order was inserted!');
                setOpen(false);
            })
            .catch(error => {
                // Handle errors
                console.error('Error:', error);
                alert('The order that you\'re trying to insert is not valid!');
                setOpen(false);
            });
    };

    const handleFindSubmit = (e) => {
        e.preventDefault();
        console.log('Find form submitted');
        fetch("http://localhost:8080/bills/find/" + BillsId)
            .then(response => {
                if (!response.ok) {
                    throw new Error('The order doesn\'t exist!');
                }
                return response.json();
            })
            .then(data => {
                // Set found order data and flag to true
                setFoundOrder(data);
                setOrderFound(true);
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Error finding order: ' + error.message);
            });
    };

    return (
        <Box sx={{ width: 500 }}>
            <BottomNavigation
                showLabels
                value={value}
                onChange={(event, newValue) => {
                    setValue(newValue);
                }}
            >
                <BottomNavigationAction label="Add" icon={<AddIcon />} onClick={handleAddClick} />
                <BottomNavigationAction label="Find by ID" icon={<SearchIcon />} onClick={handleFindClick} />
            </BottomNavigation>

            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>{dialogTitle}</DialogTitle>
                <DialogContent>
                    {dialogTitle === 'Add Order' && (
                        <form onSubmit={handleAddSubmit}>
                            <TextField label="Client ID" variant="outlined" value={Orders.client_id} onChange={(e) => setOrders({...Orders, client_id: e.target.value})}
                                       sx={{width: '100%', marginBottom: '1rem', marginTop: '1rem'}}/>
                            <TextField label="Product ID" variant="outlined" value={Orders.product_id} onChange={(e) => setOrders({...Orders, product_id: e.target.value})}
                                       sx={{width: '100%', marginBottom: '1rem', marginTop: '1rem'}}/>
                            <TextField label="Quantity" variant="outlined" value={Orders.quantity} onChange={(e) => setOrders({...Orders, quantity: e.target.value})}
                                       sx={{width: '100%', marginBottom: '1rem', marginTop: '1rem'}}/>
                            <TextField label="Order Date" variant="outlined" type="date" value={Orders.order_date} onChange={(e) => setOrders({...Orders, order_date: e.target.value})}
                                       InputLabelProps={{shrink: true,}}
                                       sx={{width: '100%', marginBottom: '1rem', marginTop: '1rem'}}/>
                            <Button type="submit">Submit</Button>
                            <Button onClick={handleClose}>Cancel</Button>
                        </form>
                    )}

                    {dialogTitle === 'Find by Id' && (
                        <form onSubmit={handleFindSubmit}>
                            <TextField label="ID" variant="outlined" value={BillsId}
                                       onChange={(e) => setBillsId(e.target.value)}
                                       sx={{width: '100%', marginBottom: '1rem', marginTop: '1rem'}}/>
                            <Button type="submit">Submit</Button>
                            <Button onClick={handleClose}>Cancel</Button>
                        </form>
                    )}

                    {orderFound && foundOrder && (
                        <Box sx={{border: '1px solid black', padding: '1rem', marginTop: '1rem'}}>
                            {Object.entries(foundOrder).map(([key, value]) => (
                                <div key={key} style={{marginBottom: '0.5rem'}}>
                                    <div style={{
                                        backgroundColor: '#f0f0f0',
                                        padding: '0.2rem 0.5rem',
                                        borderRadius: '5px',
                                        display: 'inline-block',
                                        marginRight: '0.5rem'
                                    }}>
                                        {key.charAt(0).toUpperCase() + key.slice(1)}:
                                    </div>
                                    <span>{value}</span>
                                </div>
                            ))}
                            <Button onClick={() => setOrderFound(false)}>Close</Button>
                        </Box>
                    )}
                </DialogContent>
            </Dialog>
        </Box>
    );
}
