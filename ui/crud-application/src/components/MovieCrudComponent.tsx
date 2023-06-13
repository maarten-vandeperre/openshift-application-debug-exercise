import * as React from 'react';
import {useEffect, useState} from 'react';
import {styled, Theme, useTheme} from '@mui/material/styles';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, {tableCellClasses} from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import {Button, Input, TextField} from "@mui/material";
import SaveIcon from '@mui/icons-material/Save';
import RefreshIcon from '@mui/icons-material/Refresh';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select, {SelectChangeEvent} from '@mui/material/Select';
import Chip from '@mui/material/Chip';
import {useDispatch, useSelector} from "react-redux";
import {fetchMovieData, postMovieData} from "../redux/MovieServiceDataReducer";
const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
    PaperProps: {
        style: {
            maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
            width: 250,
        },
    },
};

interface Props {

}

interface Pair {
    ref: string
    name: string
}

const ACTORS: Pair[] = [
    {ref: "13ed6a67-a4c4-4307-85da-2accbcf25aa7", name: "Maarten Vandeperre"},
    {ref: "23ed6a67-a4c4-4307-85da-2accbcf25aa7", name: "Pieter Vandeperre"},
    {ref: "33ed6a67-a4c4-4307-85da-2accbcf25aa7", name: "Bart Joris"},
]

const CATEGORIES: Pair[] = [
    {ref: "45b2dd57-e985-411c-aa67-1f4981f1b6cf", name: "Action"},
    {ref: "605f08b0-0166-4457-8481-08a3c44fe1ec", name: "Thriller"},
    {ref: "f13603d6-21e4-4ba3-8678-9f518dabd67c", name: "Drama"},
    {ref: "47828dc2-e0cb-4bd1-ba7a-c165c0f6a760", name: "Romance"},
]

export default function MovieCrudComponent(props: Props) {
    const theme = useTheme();

    const [peopleRequestCount, setPeopleRequestCount] = useState(0);
    const [name, setName] = useState("");
    const [actors, setActors] = useState<string[]>([]);
    const [categories, setCategories] = useState<string[]>([]);

    const [nameError, setNameError] = useState("");

    const dispatch = useDispatch();
    useEffect(() => {
        // @ts-ignore
        dispatch(fetchMovieData())
        setTimeout(
            () => {
                // @ts-ignore
                dispatch(fetchMovieData())
            },
            1500
        )
    }, [peopleRequestCount])
    const movieServiceData = useSelector((state: any) => state.movieServiceData);

    const validateFormSubmit = () => {
        setNameError("");

        let error = false
        if (name === "") {
            error = true;
            setNameError("Name is required")
        }
        if (!error) {
            // @ts-ignore
            dispatch(postMovieData(name, actors, categories))
            setPeopleRequestCount(peopleRequestCount + 1)
        }
    }

    const handleActorsChange = (event: SelectChangeEvent<typeof actors>) => {
        const {
            target: {value},
        } = event;
        setActors(
            // On autofill we get a stringified value.
            typeof value === 'string' ? value.split(',') : value,
        );
    };

    const handleCategoriesChange = (event: SelectChangeEvent<typeof categories>) => {
        const {
            target: {value},
        } = event;
        setCategories(
            // On autofill we get a stringified value.
            typeof value === 'string' ? value.split(',') : value,
        );
    };

    const actorNames = ACTORS.filter(a => actors.indexOf(a.ref) !== -1).map(a => a.name)
    const categoryNames = CATEGORIES.filter(c => categories.indexOf(c.ref) !== -1).map(c => c.name)

    return (
        <div className="movie-crud-component" style={{width: '1000px'}}>
            <Box sx={{flexGrow: 1}}>
                <Grid container spacing={2}>
                    <Grid item xs={3}>
                        <FormControl sx={{m: 1, width: 300}}>
                            <TextField label="Name*" variant="standard" fullWidth
                                       value={name} onChange={e => setName(e.target.value)}
                                       error={nameError !== ""} helperText={nameError}
                            />
                        </FormControl>
                    </Grid>
                    <Grid item xs={6}>&nbsp;</Grid>
                    <Grid item xs={6}>
                        <FormControl sx={{m: 1, width: 300}}>
                            <InputLabel id="demo-multiple-chip-label">Actors</InputLabel>
                            <Select
                                labelId="Actors"
                                id="demo-multiple-chip-actors"
                                multiple
                                variant="standard"
                                value={actors}
                                onChange={handleActorsChange}
                                input={<Input id="select-multiple-chip-actors"/>}
                                renderValue={(selected) => (
                                    <Box sx={{display: 'flex', flexWrap: 'wrap', gap: 0.5}}>
                                        {actorNames.map((value) => (
                                            <Chip key={value} label={value}/>
                                        ))}
                                    </Box>
                                )}
                                MenuProps={MenuProps}
                            >
                                {ACTORS.map((actor: Pair) => (
                                    <MenuItem
                                        key={actor.ref}
                                        value={actor.ref}
                                        style={getStyles(actor.ref, ACTORS, theme)}
                                    >
                                        {actor.name}
                                    </MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                    </Grid>
                    <Grid item xs={6}>
                        <FormControl sx={{m: 1, width: 300}}>
                            <InputLabel id="demo-multiple-chip-label">Categories</InputLabel>
                            <Select
                                labelId="Categories"
                                id="demo-multiple-chip-categories"
                                multiple
                                variant="standard"
                                value={categories}
                                onChange={handleCategoriesChange}
                                input={<Input id="select-multiple-chip-categories"/>}
                                renderValue={(selected) => (
                                    <Box sx={{display: 'flex', flexWrap: 'wrap', gap: 0.5}}>
                                        {categoryNames.map((value) => (
                                            <Chip key={value} label={value}/>
                                        ))}
                                    </Box>
                                )}
                                MenuProps={MenuProps}
                            >
                                {CATEGORIES.map((actor: Pair) => (
                                    <MenuItem
                                        key={actor.ref}
                                        value={actor.ref}
                                        style={getStyles(actor.ref, CATEGORIES, theme)}
                                    >
                                        {actor.name}
                                    </MenuItem>
                                ))}
                            </Select>
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
                            <StyledTableCell>Ref</StyledTableCell>
                            <StyledTableCell align="left">Name</StyledTableCell>
                            <StyledTableCell align="left">Categories</StyledTableCell>
                            <StyledTableCell align="left">Categories</StyledTableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {movieServiceData.movies.map((data: any) => (
                            <StyledTableRow key={data.ref}>
                                <StyledTableCell component="th" scope="row">
                                    {data.ref}
                                </StyledTableCell>
                                <StyledTableCell align="left">{data.name}</StyledTableCell>
                                <StyledTableCell align="left">
                                    <ul>
                                        {data.categories.map((a: any) => {
                                            return <li>{a}</li>
                                        })}
                                    </ul>
                                </StyledTableCell>
                                <StyledTableCell align="left">
                                    <ul>
                                        {data.categories.map((c: any) => {
                                            return <li>{c}</li>
                                        })}
                                    </ul>
                                </StyledTableCell>
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

function getStyles(ref: string, pairs: readonly Pair[], theme: Theme) {
    const refs = pairs.map(p => p.ref);
    return {
        fontWeight:
            refs.indexOf(ref) === -1
                ? theme.typography.fontWeightRegular
                : theme.typography.fontWeightMedium,
    };
}