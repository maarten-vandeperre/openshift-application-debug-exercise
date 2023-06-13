import {combineReducers} from "@reduxjs/toolkit";
import PersonServiceDataReducer from "./PersonServiceDataReducer";
import MovieServiceDataReducer from "./MovieServiceDataReducer";
import MovieTrackingServiceDataReducer from "./MovieTrackingServiceDataReducer";

export default combineReducers({
    personServiceData: PersonServiceDataReducer,
    movieServiceData: MovieServiceDataReducer,
    movieTrackingServiceData: MovieTrackingServiceDataReducer
})