import {AnyAction, createSlice, Dispatch, SliceCaseReducers} from "@reduxjs/toolkit";
import axios from "axios";

export interface MovieData {
    ref: string,
    name: string
    actors: string[]
    categories: string[]
}

export interface MovieServiceData {
    movies: MovieData[]
    globalError: string
}


const slice = createSlice<MovieServiceData, SliceCaseReducers<MovieServiceData>, string>({
    name: 'MovieData',
    initialState: {
        movies: [],
        globalError: ""
    },
    reducers: {
        postMovieDataSent: (state: MovieServiceData, action: AnyAction) => {
            console.error("postMovieDataSent", state, action)
        },
        movieDataReceived: (state: MovieServiceData, action: AnyAction) => {
            if (action.payload.data) {
                state.movies = action.payload.data.map((d: any) => {
                    return {
                        ref: d.ref,
                        name: d.name,
                        actors: d.actors,
                        categories: d.categories
                    }
                })
            } else {
                state.globalError = action.payload.error
            }
        },
    }
})

export default slice.reducer

const {postMovieDataSent, movieDataReceived} = slice.actions;

export const fetchMovieData = (authorizationHeader?: string) => async (dispatch: Dispatch) => {
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
            `${process.env.REACT_APP_MOVIE_SERVICE_BASE_URL}/api/movies${process.env.REACT_APP_MOVIE_SERVICE_USER_KEY}`,
            httpConfig
        );
        console.info("fetchMovieData", data)
        if (data.status === 200) {
            result = dispatch(movieDataReceived({data: data.data}))
        } else {
            result = dispatch(movieDataReceived({error: "todo"}))
        }
    } catch (e: any) {
        console.error(e);
        //TODO global error
        result = dispatch(movieDataReceived({error: "todo"}))
    }
    return result
}

export const postMovieData = (name: string, actors: string[], categories: string[], authorizationHeader ?: string) => async (dispatch: Dispatch) => {
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
    const json = JSON.stringify({name: name, actors: actors, categories: categories});
    const data = await axios.post(
        `${process.env.REACT_APP_MOVIE_SERVICE_BASE_URL}/api/movies${process.env.REACT_APP_MOVIE_SERVICE_USER_KEY}`,
        json,
        httpConfig
    );
    console.info("postMovieData", data)
    return dispatch(postMovieDataSent(null))
}