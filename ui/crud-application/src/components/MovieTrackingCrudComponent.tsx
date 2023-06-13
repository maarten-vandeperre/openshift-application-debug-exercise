import * as React from 'react';
import {useEffect, useState} from 'react';
import {styled} from '@mui/material/styles';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, {tableCellClasses} from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import {Button, FormControl, FormHelperText, InputLabel, TextField} from "@mui/material";
import SaveIcon from '@mui/icons-material/Save';
import RefreshIcon from '@mui/icons-material/Refresh';
import {useDispatch, useSelector} from "react-redux";
import {fetchMovieTrackingData, postMovieTrackingData} from "../redux/MovieTrackingServiceDataReducer";
import MenuItem from "@mui/material/MenuItem";
import Select from "@mui/material/Select";
import {fetchPersonData} from "../redux/PersonServiceDataReducer";
import {fetchMovieData} from "../redux/MovieServiceDataReducer";

interface Props {

}

interface Pair {
    ref: string
    name: string
}

const ACTIONS: Pair[] = [
    {ref: "43339c83-f83a-4555-9d7d-636c2d5f41fd", name: "COMPLETED"},
    {ref: "3121d1ba-f1ad-4a2e-a66f-e0e4143ab929", name: "STOPPED_BEFORE_HALF"},
    {ref: "54c52665-1431-47da-9c00-94119739ab1e", name: "STOPPED_BEFORE_END"},
    {ref: "b7b31abb-ddae-4f61-af95-9d95b5ad3d8a", name: "RESUMED"},
    {ref: "b82f3c9a-932c-45f8-b169-6870b9ce957c", name: "LIKED"},
    {ref: "f44505c2-354f-4b2d-802d-93527519662f", name: "DISLIKED"}
]
export default function MovieTrackingCrudComponent(props: Props) {

    const [movieTrackingRequestCount, setMovieTrackingRequestCount] = useState(0);
    const [person, setPerson] = useState("");
    const [movie, setMovie] = useState("");
    const [action, setAction] = useState("");

    const [personError, setPersonError] = useState("");
    const [movieError, setMovieError] = useState("");
    const [actionError, setActionError] = useState("");

    const dispatch = useDispatch();
    useEffect(() => {
        // @ts-ignore
        dispatch(fetchMovieTrackingData())
        // @ts-ignore
        dispatch(fetchPersonData())
        // @ts-ignore
        dispatch(fetchMovieData())
        setTimeout(
            () => {
                // @ts-ignore
                dispatch(fetchMovieTrackingData())
                // @ts-ignore
                dispatch(fetchPersonData())
                // @ts-ignore
                dispatch(fetchMovieData())
            },
            500
        )
    }, [movieTrackingRequestCount])
    const movieTrackingServiceData = useSelector((state: any) => state.movieTrackingServiceData);
    const personServiceData = useSelector((state: any) => state.personServiceData);
    const movieServiceData = useSelector((state: any) => state.movieServiceData);

    const validateFormSubmit = () => {
        setPersonError("");
        setMovieError("")
        setActionError("")

        let error = false
        if (person === "") {
            error = true;
            setPersonError("Person is required")
        }
        if (movie === "") {
            error = true;
            setMovieError("Movie is required")
        }
        if (action === "") {
            error = true;
            setActionError("Action is required")
        }
        if (!error) {
            // @ts-ignore
            dispatch(postMovieTrackingData(person, movie, action))
            setMovieTrackingRequestCount(movieTrackingRequestCount + 1)
        }
    }

    return (
        <div className="person-crud-component" style={{width: '1000px'}}>
            <Box sx={{flexGrow: 1}}>
                <Grid container spacing={2}>
                    <Grid item xs={4}>
                        <FormControl variant="standard" sx={{m: 1, minWidth: 250}}>
                            <InputLabel id="demo-simple-select-standard-label-person">Person*</InputLabel>
                            <Select
                                labelId="demo-simple-select-standard-label-person"
                                id="demo-simple-select-standard-person"
                                value={person} onChange={(e: any) => setPerson(e.target.value)}
                                label="Person"
                            >
                                <MenuItem value="">
                                    <em>None</em>
                                </MenuItem>
                                {personServiceData.people.map((p: any) => {
                                    return <MenuItem value={p.ref}>{`${p.firstName} ${p.lastName}`}</MenuItem>;
                                })}
                            </Select>
                            {personError !== "" && <FormHelperText>{personError}</FormHelperText>}
                        </FormControl>
                    </Grid>
                    <Grid item xs={4}>
                        <FormControl variant="standard" sx={{m: 1, minWidth: 250}}>
                            <InputLabel id="demo-simple-select-standard-label-movie">Movie*</InputLabel>
                            <Select
                                labelId="demo-simple-select-standard-label-movie"
                                id="demo-simple-select-standard-movie"
                                value={movie} onChange={(e: any) => setMovie(e.target.value)}
                                label="Movie"
                            >
                                <MenuItem value="">
                                    <em>None</em>
                                </MenuItem>
                                {movieServiceData.movies.map((m: any) => {
                                    return <MenuItem value={m.ref}>{`${m.name}`}</MenuItem>;
                                })}
                            </Select>
                            {movieError !== "" && <FormHelperText>{movieError}</FormHelperText>}
                        </FormControl>
                    </Grid>
                    <Grid item xs={4}>
                        <FormControl variant="standard" sx={{m: 1, minWidth: 250}}>
                            <InputLabel id="demo-simple-select-standard-label-action">Action*</InputLabel>
                            <Select
                                labelId="demo-simple-select-standard-label-action"
                                id="demo-simple-select-standard-action"
                                value={action} onChange={(e: any) => setAction(e.target.value)}
                                label="Action"
                            >
                                <MenuItem value="">
                                    <em>None</em>
                                </MenuItem>
                                {ACTIONS.map((a: any) => {
                                    return <MenuItem value={a.ref}>{`${a.name}`}</MenuItem>;
                                })}
                            </Select>
                            {actionError !== "" && <FormHelperText>{actionError}</FormHelperText>}
                        </FormControl>
                    </Grid>
                    <Grid item xs={6}>
                        &nbsp;
                    </Grid>
                    <Grid item xs={12}>
                        <Button variant="outlined" startIcon={<SaveIcon/>} onClick={validateFormSubmit}>
                            Save
                        </Button>
                    </Grid>
                    <Grid item xs={12}><br/></Grid>
                </Grid>
            </Box>
            <hr/>
            <Button variant="outlined" startIcon={<RefreshIcon/>} onClick={() => {
                // @ts-ignore
                dispatch(fetchMovieTrackingData())
                // @ts-ignore
                dispatch(fetchPersonData())
                // @ts-ignore
                dispatch(fetchMovieData())
            }
            }>
                Refresh
            </Button>
            <br/>
            <br/>
            <TableContainer component={Paper}>
                <Table sx={{minWidth: 700}} aria-label="customized table">
                    <TableHead>
                        <TableRow>
                            <StyledTableCell align="left">Person</StyledTableCell>
                            <StyledTableCell align="left">Movie</StyledTableCell>
                            <StyledTableCell align="left">Action</StyledTableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {movieTrackingServiceData.trackingRecords.map((data: any) => (
                            <StyledTableRow key={data.ref}>
                                <StyledTableCell
                                    align="left">{personServiceData.people.filter((p: any) => p.ref === data.person).map((p: any) => `${p.firstName} ${p.lastName}`)[0]}</StyledTableCell>
                                <StyledTableCell align="left">{movieServiceData.movies.filter((m: any) => m.ref === data.movie).map((m:any) => m.name)[0]}</StyledTableCell>
                                <StyledTableCell align="left">{ACTIONS.filter(a => a.ref === data.action).map((a: any) => a.name)[0]}</StyledTableCell>
                            </StyledTableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </div>
    )
}

const StyledTableCell = styled(TableCell)(({theme}) => ({
    [`&.${tableCellClasses.head}`]: {
        backgroundColor: theme.palette.common.black,
        color: theme.palette.common.white,
    },
    [`&.${tableCellClasses.body}`]: {
        fontSize: 14,
    },
}));

const StyledTableRow = styled(TableRow)(({theme}) => ({
    '&:nth-of-type(odd)': {
        backgroundColor: theme.palette.action.hover,
    },
    // hide last border
    '&:last-child td, &:last-child th': {
        border: 0,
    },
}));