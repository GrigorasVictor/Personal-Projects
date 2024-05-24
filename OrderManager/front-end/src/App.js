import React from 'react';
import { Route, Routes, useNavigate } from 'react-router-dom';
import { createTheme, ThemeProvider } from '@mui/material/styles';

import NavigationBar from './components/navigationBar/NavigationBar';
import Client from './components/client/Client';
import Product from './components/products/Product';
import Order from './components/orders/Order';
import Home from './components/home/Home';
import Bill from './components/bill/Bill';

const theme = createTheme({
    palette: {
        primary: {
            main: '#1a237e',
        },
        secondary: {
            main: '#3e3eff',
        },
    },
});

function App() {
    const navigate = useNavigate();

    // Redirect to the home page when accessing the root URL

    return (
        <ThemeProvider theme={theme}>
            <div className="App">
                <NavigationBar />
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/home" element={<Home />} />
                    <Route path="/clients" element={<Client />} />
                    <Route path="/products" element={<Product />} />
                    <Route path="/orders" element={<Order />} />
                    <Route path="/bills" element={<Bill />} />
                </Routes>
            </div>
        </ThemeProvider>
    );
}

export default App;