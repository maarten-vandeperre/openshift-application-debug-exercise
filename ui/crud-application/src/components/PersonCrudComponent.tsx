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
import {Button, TextField} from "@mui/material";
import SaveIcon from '@mui/icons-material/Save';
import RefreshIcon from '@mui/icons-material/Refresh';
import {useDispatch, useSelector} from "react-redux";
import {fetchPersonData, postPersonData} from "../redux/PersonServiceDataReducer";

interface Props {

}

export default function PersonCrudComponent(props: Props) {

    const [peopleRequestCount, setPeopleRequestCount] = useState(0);
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [birthDate, setBirthDate] = useState("");

    const [firstNameError, setFirstNameError] = useState("");
    const [lastNameError, setLastNameError] = useState("");
    const [birthDateError, setBirthDateError] = useState("");

    const dispatch = useDispatch();
    useEffect(() => {
        // @ts-ignore
        dispatch(fetchPersonData())
        setTimeout(
            () => {
                // @ts-ignore
                dispatch(fetchPersonData())
            },
            1500
        )
    }, [peopleRequestCount])
    const personServiceData = useSelector((state: any) => state.personServiceData);

    const validateFormSubmit = () => {
        setFirstNameError("");
        setLastNameError("")
        setBirthDateError("")

        let error = false
        if (firstName === "") {
            error = true;
            setFirstNameError("First name is required")
        }
        if (lastName === "") {
            error = true;
            setLastNameError("Last name is required")
        }
        if (birthDate === "") {
            error = true;
            setBirthDateError("Birth date is required")
        }
        if (!error) {
            // @ts-ignore
            dispatch(postPersonData(firstName, lastName, birthDate))
            setPeopleRequestCount(peopleRequestCount + 1)
        }
    }

    return (
        <div className="person-crud-component" style={{width: '1000px'}}>
            <Box sx={{flexGrow: 1}}>
                <Grid container spacing={2}>
                    <Grid item xs={3}>
                        <TextField label="First Name*" variant="standard" fullWidth
                                   value={firstName} onChange={e => setFirstName(e.target.value)}
                                   error={firstNameError !== ""} helperText={firstNameError}
                        />
                    </Grid>
                    <Grid item xs={6}>
                        <TextField label="Last Name*" variant="standard" fullWidth
                                   value={lastName} onChange={e => setLastName(e.target.value)}
                                   error={lastNameError !== ""} helperText={lastNameError}
                        />
                    </Grid>
                    <Grid item xs={6}>
                        <TextField label="Birth Date (yyyy-mm-dd)*" variant="standard" fullWidth
                                   value={birthDate} onChange={e => setBirthDate(e.target.value)}
                                   error={birthDateError !== ""} helperText={birthDateError}
                        />
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
                dispatch(fetchPersonData())
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
                            <StyledTableCell align="left">First Name</StyledTableCell>
                            <StyledTableCell align="left">Last Name</StyledTableCell>
                            <StyledTableCell align="left">Birth Date</StyledTableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {personServiceData.people.map((data: any) => (
                            <StyledTableRow key={data.ref}>
                                <StyledTableCell component="th" scope="row">
                                    {data.ref}
                                </StyledTableCell>
                                <StyledTableCell align="left">{data.firstName}</StyledTableCell>
                                <StyledTableCell align="left">{data.lastName}</StyledTableCell>
                                <StyledTableCell align="left">{data.birthDate}</StyledTableCell>
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