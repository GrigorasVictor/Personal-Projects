import React, { useEffect, useState } from 'react';
import { BarChart } from '@mui/x-charts/BarChart';

function ChartsOverviewDemo() {
    // State variables to store data for clients, orders, and products
    const [clientsData, setClientsData] = useState([]);
    const [ordersData, setOrdersData] = useState([]);
    const [productsData, setProductsData] = useState([]);

    // Fetch data from API
    useEffect(() => {
        // Function to fetch clients data
        const fetchClientsData = async () => {
            try {
                const response = await fetch('http://localhost:8080/clients/getAll');
                const data = await response.json();
                console.log('Clients data:', data); // Log the fetched data
                setClientsData(data);
            } catch (error) {
                console.error('Error fetching clients data:', error);
            }
        };

        // Function to fetch orders data
        const fetchOrdersData = async () => {
            try {
                const response = await fetch('http://localhost:8080/orders/getAll');
                const data = await response.json();
                console.log('Orders data:', data); // Log the fetched data
                setOrdersData(data);
            } catch (error) {
                console.error('Error fetching orders data:', error);
            }
        };

        // Function to fetch products data
        const fetchProductsData = async () => {
            try {
                const response = await fetch('http://localhost:8080/products/getAll');
                const data = await response.json();
                console.log('Products data:', data); // Log the fetched data
                setProductsData(data);
            } catch (error) {
                console.error('Error fetching products data:', error);
            }
        };

        // Call the fetch functions
        fetchClientsData();
        fetchOrdersData();
        fetchProductsData();
    }, []);

    // Create series data for the chart
    const seriesData = [
        { data: [clientsData.length, 0, 0] }, // Set the number of clients
        { data: [0, ordersData.length, 0] }, // Set the number of orders
        { data: [0, 0, productsData.length] }, // Set the number of products
    ];

    return (
        <div style={{ textAlign: 'center', fontSize: '24px', fontWeight: 'bold', marginBottom: '20px' }}>
            <BarChart
                series={seriesData}
                height={290}
                xAxis={[{ data: ['Clients', 'Orders', 'Products'], scaleType: 'band' }]}
                margin={{ top: 10, bottom: 30, left: 40, right: 10 }}
            />
        </div>
    );
}

export default ChartsOverviewDemo;
