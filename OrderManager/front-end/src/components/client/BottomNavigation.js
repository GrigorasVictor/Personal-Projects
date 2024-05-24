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
    const [clientId, setClientId] = React.useState('');
    const [foundClient, setFoundClient] = React.useState(null); // State to hold found client data
    const [clientFound, setClientFound] = React.useState(false); // Flag to indicate if client was found
    const [client, setClient] = React.useState({
        id: 0,
        client_name: '',
        email: '',
        phone: '',
        address: ''
    });

    const handleAddClick = () => {
        setDialogTitle('Add Client');
        setOpen(true);
    };

    const handleEditClick = () => {
        setDialogTitle('Edit Client');
        setOpen(true);
    };

    const handleDeleteClick = () => {
        setDialogTitle('Delete Client');
        setOpen(true);
    };

    const handleFindClick = () => {
        setDialogTitle('Find Client');
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleAddSubmit = (e) => {
        e.preventDefault();
        console.log('Add form submitted');
        fetch("http://localhost:8080/clients/add", {
            method: "POST",
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(client)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Internal server error');
                }
                alert('The client was inserted!');
                setOpen(false);
            })
            .catch(error => {
                // Handle errors
                console.error('Error:', error);
                alert('The client that you\'re trying to insert is not valid!');
                setOpen(false);
            });
    };

    const handleEditSubmit = (e) => {
        e.preventDefault();
        console.log('Edit form submitted');
        fetch("http://localhost:8080/clients/edit", {
            method: "POST",
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(client)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Internal server error');
                }
                alert('The client was updated!');
                setOpen(false);
            })
            .catch(error => {
                // Handle errors
                console.error('Error:', error);
                alert('The client that you\'re trying to update is not valid!');
                setOpen(false);
            });
    };

    const handleDeleteSubmit = (e) => {
        e.preventDefault();
        console.log('Delete form submitted');
        fetch("http://localhost:8080/clients/delete/" + clientId, {
            method: "DELETE"
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('The client doesn\'t exist!');
                }
                return response.text();
            })
            .then(message => {
                alert('Client deletion successful!');
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Error deleting client: ' + error.message);
            });
        setOpen(false);
    };

    const handleFindSubmit = (e) => {
        e.preventDefault();
        console.log('Find form submitted');
        fetch("http://localhost:8080/clients/find/" + clientId)
            .then(response => {
                if (!response.ok) {
                    throw new Error('The client doesn\'t exist!');
                }
                return response.json();
            })
            .then(data => {
                // Set found client data and flag to true
                setFoundClient(data);
                setClientFound(true);
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Error finding client: ' + error.message);
            });
    }

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
                    {dialogTitle === 'Add Client' && (
                        <form onSubmit={handleAddSubmit}>
                            <TextField label="Client Name" variant="outlined" value={client.client_name} onChange={(e) => setClient({ ...client, client_name: e.target.value })} sx={{ width: '100%', marginBottom: '1rem', marginTop: '1rem' }} />
                            <TextField label="Email" variant="outlined" value={client.email} onChange={(e) => setClient({ ...client, email: e.target.value })} sx={{ width: '100%', marginBottom: '1rem', marginTop: '1rem' }} />
                            <TextField label="Phone" variant="outlined" value={client.phone} onChange={(e) => setClient({ ...client, phone: e.target.value })} sx={{ width: '100%', marginBottom: '1rem', marginTop: '1rem' }} />
                            <TextField label="Address" variant="outlined" value={client.address} onChange={(e) => setClient({ ...client, address: e.target.value })} sx={{ width: '100%', marginBottom: '1rem', marginTop: '1rem' }} />
                            <Button type="submit">Submit</Button>
                            <Button onClick={handleClose}>Cancel</Button>
                        </form>
                    )}
                    {dialogTitle === 'Edit Client' && (
                        <form onSubmit={handleEditSubmit}>
                            <TextField label="ID" variant="outlined" value={client.id} onChange={(e) => setClient({ ...client, id: e.target.value })} sx={{ width: '100%', marginBottom: '1rem', marginTop: '1rem' }} />
                            <TextField label="Client Name" variant="outlined" value={client.client_name} onChange={(e) => setClient({ ...client, client_name: e.target.value })} sx={{ width: '100%', marginBottom: '1rem', marginTop: '1rem' }} />
                            <TextField label="Email" variant="outlined" value={client.email} onChange={(e) => setClient({ ...client, email: e.target.value })} sx={{ width: '100%', marginBottom: '1rem', marginTop: '1rem' }} />
                            <TextField label="Phone" variant="outlined" value={client.phone} onChange={(e) => setClient({ ...client, phone: e.target.value })} sx={{ width: '100%', marginBottom: '1rem', marginTop: '1rem' }} />
                            <TextField label="Address" variant="outlined" value={client.address} onChange={(e) => setClient({ ...client, address: e.target.value })} sx={{ width: '100%', marginBottom: '1rem', marginTop: '1rem' }} />
                            <Button type="submit">Submit</Button>
                            <Button onClick={handleClose}>Cancel</Button>
                        </form>
                    )}
                    {dialogTitle === 'Delete Client' && (
                        <form onSubmit={handleDeleteSubmit}>
                            <TextField label="ID" variant="outlined" value={clientId} onChange={(e) => setClientId(e.target.value)} sx={{ width: '100%', marginBottom: '1rem', marginTop: '1rem' }} />
                            <Button type="submit">Submit</Button>
                            <Button onClick={handleClose}>Cancel</Button>
                        </form>
                    )}

                    {dialogTitle === 'Find Client' && (
                        <form onSubmit={handleFindSubmit}>
                            <TextField label="ID" variant="outlined" value={clientId} onChange={(e) => setClientId(e.target.value)} sx={{ width: '100%', marginBottom: '1rem', marginTop: '1rem' }} />
                            <Button type="submit">Submit</Button>
                            <Button onClick={handleClose}>Cancel</Button>
                        </form>
                    )}
                    {clientFound && foundClient && (
                        <Box sx={{ border: '1px solid black', padding: '1rem', marginTop: '1rem' }}>
                            {Object.entries(foundClient).map(([key, value]) => (
                                <div key={key} style={{ marginBottom: '0.5rem' }}>
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
                            <Button onClick={() => setClientFound(false)}>Close</Button>
                        </Box>
                    )}
                </DialogContent>
            </Dialog>
        </Box>
    );
}
