import React from 'react';
import Lottie from 'lottie-react';
import animationData from '../../assets/AnimationIndexPage.json';
import DataChart from './DataChart';

function Home() {
    return (
        <div style={{ display: 'flex', alignItems: 'center' }}>
            {/* Left box for text */}
            <div style={{ flex: 1 }}>
                <div style={{ padding: '20px', textAlign: 'center', backgroundColor: '#f0f0f0', borderRadius: '10px', boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)' }}>
                    {/* Welcome text inside a box */}
                    <div style={{ padding: '20px' }}>
                        <h1 style={{ fontSize: '3rem', fontWeight: 'bold', color: '#333', margin: 0 }}>Welcome!</h1>
                    </div>
                </div>
                <div style={{ marginTop: '20px', textAlign: 'center' }}>
                    {/* Styling for statistics text */}
                    <p style={{ fontSize: '1.2rem', color: '#555' }}>Here are some statistics:</p>
                </div>
                <DataChart />
            </div>
            {/* Right box for animation */}
            <div style={{ flex: 1, textAlign: 'right' }}>
                <div style={{ width: '100%', height: '100%' }}>
                    <Lottie
                        animationData={animationData}
                        style={{ width: '100%', height: '100%' }} // Fill animation to 100% of its container
                    />
                </div>
            </div>
        </div>
    );
}

export default Home;
