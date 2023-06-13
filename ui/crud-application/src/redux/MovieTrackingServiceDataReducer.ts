import {AnyAction, createSlice, Dispatch, SliceCaseReducers} from "@reduxjs/toolkit";
import axios from "axios";

export interface MovieTrackingData {
    person: string
    movie: string
    action: string
}

export interface MovieTrackingServiceData {
    trackingRecords: MovieTrackingData[]
    globalError: string
}


const slice = createSlice<MovieTrackingServiceData, SliceCaseReducers<MovieTrackingServiceData>, string>({
    name: 'MovieTrackingData',
    initialState: {
        trackingRecords: [],
        globalError: ""
    },
    reducers: {
        postMovieTrackingRecordDataSent: (state: MovieTrackingServiceData, action: AnyAction) => {
            console.error("postMovieTrackingRecordDataSent", state, action)
        },
        movieTrackingDataReceived: (state: MovieTrackingServiceData, action: AnyAction) => {
            if (action.payload.data) {
                state.trackingRecords = action.payload.data.map((d: any) => {
                    return {
                        person: d.person,
                        movie: d.movie,
                        action: d.action
                    }
                })
            } else {
                state.globalError = action.payload.error
            }
        },
    }
})

export default slice.reducer

const {postMovieTrackingRecordDataSent, movieTrackingDataReceived} = slice.actions;

export const fetchMovieTrackingData = (authorizationHeader?: string) => async (dispatch: Dispatch) => {
    let result
    try {
        let httpConfig = {};
        if (authorizationHeader) {
            httpConfig = {
                ...httpConfig,
                "headers": {
                    "Authorization": `Bearer ${authorizationHeader}`
                }
            }
        }
        console.info("httpConfig", httpConfig)
        const data = await axios.get(
            `${process.env.REACT_APP_MOVIE_TRACKING_SERVICE_BASE_URL}/api/movie-tracking-records${process.env.REACT_APP_MOVIE_TRACKING_SERVICE_USER_KEY}`,
            httpConfig
        );
        console.info("fetchMovieTrackingData", data)
        if (data.status === 200) {
            result = dispatch(movieTrackingDataReceived({data: data.data}))
        } else {
            result = dispatch(movieTrackingDataReceived({error: "todo"}))
        }
    } catch (e: any) {
        console.error(e);
        //TODO global error
        result = dispatch(movieTrackingDataReceived({error: "todo"}))
    }
    return result
}

export const postMovieTrackingData = (person: string, movie: string, action: string, authorizationHeader ?: string) => async (dispatch: Dispatch) => {
    let httpConfig = {};
    if (authorizationHeader) {
        httpConfig = {
            ...httpConfig,
            "headers": {
                "Authorization": `Bearer ${authorizationHeader}`,
                "Content-Type": "application/json"
            }
        }
    } else {
        httpConfig = {
            ...httpConfig,
            "headers": {
                "Content-Type": "application/json"
            }
        }
    }
    const json = JSON.stringify({person: person, movie: movie, action: action});
    const data = await axios.post(
        `${process.env.REACT_APP_MOVIE_TRACKING_SERVICE_BASE_URL}/api/movie-tracking-records${process.env.REACT_APP_MOVIE_TRACKING_SERVICE_USER_KEY}`,
        json,
        httpConfig
    );
    console.info("postMovieTrackingRecordData", data)
    return dispatch(postMovieTrackingRecordDataSent(null))
}