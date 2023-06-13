import React from 'react';
import './App.css';
import MainScreen from "./screens/MainScreen";
import {createTheme, ThemeProvider} from "@mui/material";
import "./Global.scss"
import { Provider } from 'react-redux';
import {store} from "./redux/Store";

const theme = createTheme({
    palette: {
        primary: {
            main: '#ee0000'
        }
    }
});

function App() {
    return (
        <Provider store={store}>
            <div className="app-root">
                <ThemeProvider theme={theme}>
                    <MainScreen/>
                </ThemeProvider>
            </div>
        </Provider>
    );
}

export default App;
