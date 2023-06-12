import {AnyAction, createSlice, Dispatch, SliceCaseReducers} from "@reduxjs/toolkit";
import axios from "axios";

export interface PersonData {
    ref: string,
    firstName: string
    lastName: string
    birthDate: string
}

export interface PersonServiceData {
    people: PersonData[]
    globalError: string
}


const slice = createSlice<PersonServiceData, SliceCaseReducers<PersonServiceData>, string>({
    name: 'PersonData',
    initialState: {
        people: [],
        globalError: ""
    },
    reducers: {
        postPersonDataSent: (state: PersonServiceData, action: AnyAction) => {
            console.error("postPersonDataSent", state, action)
        },
        personDataReceived: (state: PersonServiceData, action: AnyAction) => {
            if (action.payload.data) {
                state.people = action.payload.data.map((d: any) => {
                    return {
                        ref: d.ref,
                        firstName: d.firstName,
                        lastName: d.lastName,
                        birthDate: d.birthDate
                    }
                })
            } else {
                state.globalError = action.payload.error
            }
        },
    }
})

export default slice.reducer

const {postPersonDataSent, personDataReceived} = slice.actions;

export const fetchPersonData = (authorizationHeader?: string) => async (dispatch: Dispatch) => {
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
            `${process.env.REACT_APP_PERSON_SERVICE_BASE_URL}/api/people${process.env.REACT_APP_PERSON_SERVICE_USER_KEY}`,
            httpConfig
        );
        console.info("fetchPersonData", data)
        if (data.status === 200) {
            result = dispatch(personDataReceived({data: data.data}))
        } else {
            result = dispatch(personDataReceived({error: "todo"}))
        }
    } catch (e: any) {
        console.error(e);
        //TODO global error
        result = dispatch(personDataReceived({error: "todo"}))
    }
    return result
}

export const postPersonData = (firstName: string, lastName: string, birthDate: string, authorizationHeader ?: string) => async (dispatch: Dispatch) => {
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
    const json = JSON.stringify({firstName: firstName, lastName: lastName, birthDate: birthDate});
    const data = await axios.post(
        `${process.env.REACT_APP_PERSON_SERVICE_BASE_URL}/api/people${process.env.REACT_APP_PERSON_SERVICE_USER_KEY}`,
        json,
        httpConfig
    );
    console.info("postPersonData", data)
    return dispatch(postPersonDataSent(null))
}