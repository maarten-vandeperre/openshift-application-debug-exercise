import {applyMiddleware, configureStore} from '@reduxjs/toolkit'
import reducer from './Reducers';
import {routerMiddleware} from 'react-router-redux';
// @ts-ignore
import {createHistory} from 'history';
import {createLogger} from "redux-logger";
import {localStorageMiddleware, promiseMiddleware} from "./Middleware";
import {devToolsEnhancer} from "redux-devtools-extension";
import {CurriedGetDefaultMiddleware} from "@reduxjs/toolkit/src/getDefaultMiddleware";

// Build the middleware for intercepting and dispatching navigation actions
const history = createHistory();
const myRouterMiddleware = routerMiddleware(history);

const getMiddleware = () => {
    if (process.env.NODE_ENV === 'production') {
        return applyMiddleware(myRouterMiddleware, promiseMiddleware, localStorageMiddleware);
    } else {
        // Enable additional logging in non-production environments.
        return applyMiddleware(myRouterMiddleware, promiseMiddleware, localStorageMiddleware, createLogger())
    }
};

export const store = configureStore({
    reducer: reducer,
    middleware: (getDefaultMiddleware: CurriedGetDefaultMiddleware) => getDefaultMiddleware({serializableCheck: false}),
    devTools: true,
    enhancers: [devToolsEnhancer({})],});
// export const store = configureStore(reducer, composeWithDevTools(getMiddleware()));