import * as React from 'react';
import Box from '@mui/material/Box';
import BottomNavigation from '@mui/material/BottomNavigation';
import BottomNavigationAction from '@mui/material/BottomNavigationAction';
import AddIcon from '@mui/icons-material/Add';
import EditIcon from '@mui/icons-material/Edit';
import ClearIcon from '@mui/icons-material/Clear';
import SearchIcon from '@mui/icons-material/Search';
import { Dialog, DialogContent, DialogTitle, TextField } from "@mui/material";
import Button from "@mui/material/Button";

export default function SimpleBottomNavigation() {
    const [value, setValue] = React.useState(0);
    const [open, setOpen] = React.useState(false);
    const [dialogTitle, setDialogTitle] = React.useState('');
    let [ProductId,setProductId] = React.useState(0);
    const [foundProduct, setFoundProduct] = React.useState(null); // State to hold found order data
    const [productFound, setProductFound] = React.useState(false); // Flag to indicate if order was found
    let [Product, setProduct] = React.useState({
        id: 0,
        product_name: '',
        description: '',
        price: '',
        total_quantity: ''
    });

    const handleAddClick = () => {
        setDialogTitle('Add Product');
        setOpen(true);
    };

    const handleEditClick = () => {
        setDialogTitle('Edit Product');
        setOpen(true);
    };

    const handleDeleteClick = () => {
        setDialogTitle('Delete Product');
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
        fetch("http://localhost:8080/products/add", {
            method: "POST",
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(Product)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Internal server error');
                }
                alert('The Product was inserted!');
                setOpen(false);
            })
            .catch(error => {
                // Handle errors
                console.error('Error:', error);
                alert('The Product that you\'re trying to insert is not valid!');
                setOpen(false);
            });
    };

    const handleEditSubmit = (e) => {
        e.preventDefault();
        console.log('Add form submitted');
        fetch("http://localhost:8080/products/edit", {
            method: "POST",
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(Product)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Internal server error');
                }
                alert('The Product was updated!');
                setOpen(false);
            })
            .catch(error => {
                // Handle errors
                console.error('Error:', error);
                alert('The Product that you\'re trying to update is not valid!');
                setOpen(false);
            });
        setOpen(false);
    };

    const handleDeleteSubmit = (e) => {
        e.preventDefault();
        console.log('Delete form submitted');
        fetch("http://localhost:8080/products/delete/" + ProductId, {
            method: "DELETE"})
            .then(response => {
                if (!response.ok) {
                    throw new Error('The Product doesn\'t exist!');
                }
                return response.text();
            })
            .then(message => {
                alert('Product deletion successful!');
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Error deleting Product: ' + error.message);
            });
        setOpen(false);
    };
    const handleFindSubmit = (e) => {
        e.preventDefault();
        console.log('Find form submitted');
        fetch("http://localhost:8080/products/find/" + ProductId)
            .then(response => {
                if (!response.ok) {
                    throw new Error('The order doesn\'t exist!');
                }
                return response.json();
            })
            .then(data => {
                // Set found order data and flag to true
                setFoundProduct(data);
                setProductFound(true);
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
                <BottomNavigationAction label="Edit" icon={<EditIcon />} onClick={handleEditClick} />
                <BottomNavigationAction label="Delete" icon={<ClearIcon />} onClick={handleDeleteClick} />
                <BottomNavigationAction label="Find by Id" icon={<SearchIcon />} onClick={handleFindClick} />

            </BottomNavigation>

            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>{dialogTitle}</DialogTitle>
                <DialogContent>
                    {dialogTitle === 'Add Product' && (
                        <form onSubmit={handleAddSubmit}>
                            <TextField label="ID" variant="outlined" value={Product.id} onChange={(e) => setProduct({ ...Product, id: e.target.value })} sx={{ width: '100%', marginBottom: '1rem', marginTop: '1rem' }} />
                            <TextField label="Product Name" variant="outlined" value={Product.product_name} onChange={(e) => setProduct({ ...Product, product_name: e.target.value })} sx={{ width: '100%', marginBottom: '1rem', marginTop: '1rem' }} />
                            <TextField label="Description" variant="outlined" value={Product.description} onChange={(e) => setProduct({ ...Product, description: e.target.value })} sx={{ width: '100%', marginBottom: '1rem', marginTop: '1rem' }} />
                            <TextField label="Price" variant="outlined" value={Product.price} onChange={(e) => setProduct({ ...Product, price: e.target.value })} sx={{ width: '100%', marginBottom: '1rem', marginTop: '1rem' }} />
                            <TextField label="Total Quantity" variant="outlined" value={Product.total_quantity} onChange={(e) => setProduct({ ...Product, total_quantity: e.target.value })} sx={{ width: '100%', marginBottom: '1rem', marginTop: '1rem' }} />
                            <Button type="submit">Submit</Button>
                            <Button onClick={handleClose}>Cancel</Button>

                        </form>
                    )}
                    {dialogTitle === 'Edit Product' && (
                        <form onSubmit={handleEditSubmit}>
                            <TextField label="ID" variant="outlined" value={Product.id} onChange={(e) => setProduct({ ...Product, id: e.target.value })} sx={{ width: '100%', marginBottom: '1rem', marginTop: '1rem' }} />
                            <TextField label="Product Name" variant="outlined" value={Product.product_name} onChange={(e) => setProduct({ ...Product, product_name: e.target.value })} sx={{ width: '100%', marginBottom: '1rem', marginTop: '1rem' }} />
                            <TextField label="Description" variant="outlined" value={Product.description} onChange={(e) => setProduct({ ...Product, description: e.target.value })} sx={{ width: '100%', marginBottom: '1rem', marginTop: '1rem' }} />
                            <TextField label="Price" variant="outlined" value={Product.price} onChange={(e) => setProduct({ ...Product, price: e.target.value })} sx={{ width: '100%', marginBottom: '1rem', marginTop: '1rem' }} />
                            <TextField label="Total Quantity" variant="outlined" value={Product.total_quantity} onChange={(e) => setProduct({ ...Product, total_quantity: e.target.value })} sx={{ width: '100%', marginBottom: '1rem', marginTop: '1rem' }} />
                            <Button type="submit">Submit</Button>
                            <Button onClick={handleClose}>Cancel</Button>
                        </form>
                    )}
                    {dialogTitle === 'Delete Product' && (
                        <form onSubmit={handleDeleteSubmit}>
                            <TextField label="ID" variant="outlined" value={ProductId} onChange={(e) => setProductId(e.target.value)} sx={{ width: '100%', marginBottom: '1rem', marginTop: '1rem' }}/>
                            <Button type="submit">Submit</Button>
                            <Button onClick={handleClose}>Cancel</Button>
                        </form>
                    )}
                    {dialogTitle === 'Find by Id' && (
                        <form onSubmit={handleFindSubmit}>
                            <TextField label="ID" variant="outlined" value={ProductId}
                                       onChange={(e) => setProductId(e.target.value)}
                                       sx={{width: '100%', marginBottom: '1rem', marginTop: '1rem'}}/>
                            <Button type="submit">Submit</Button>
                            <Button onClick={handleClose}>Cancel</Button>
                        </form>
                    )}

                    {productFound && foundProduct && (
                        <Box sx={{border: '1px solid black', padding: '1rem', marginTop: '1rem'}}>
                            {Object.entries(foundProduct).map(([key, value]) => (
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
                            <Button onClick={() => setProductFound(false)}>Close</Button>
                        </Box>
                    )}
                </DialogContent>
            </Dialog>
        </Box>
    );
}
