import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import {SyntheticEvent, useState} from "react";
import PersonCrudComponent from "./PersonCrudComponent";
import MovieCrudComponent from "./MovieCrudComponent";
import MovieTrackingCrudComponent from "./MovieTrackingCrudComponent";

interface Props {

}

export default function TabbedContentPane(props: Props) {
    const [value, setValue] = useState(0);

    const handleChange = (event: SyntheticEvent, newValue: number) => {
        setValue(newValue);
    };

    return (
        <div className="tabbed-content-pane">
            <Box
                sx={{flexGrow: 1, bgcolor: 'background.paper', display: 'flex', height: "100%"}}
            >
                <Tabs
                    orientation="vertical"
                    variant="scrollable"
                    value={value}
                    onChange={handleChange}
                    aria-label="Vertical tabs example"
                    sx={{borderRight: 1, borderColor: 'divider'}}
                >
                    <Tab label="Person Data" {...a11yProps(0)}/>
                    <Tab label="Movie Data" {...a11yProps(1)} />
                    <Tab label="Movie Tracking Data" {...a11yProps(2)} />
                </Tabs>
                <TabPanel value={value} index={0} >
                    <PersonCrudComponent />
                </TabPanel>
                <TabPanel value={value} index={1}>
                    <MovieCrudComponent />
                </TabPanel>
                <TabPanel value={value} index={2}>
                    <MovieTrackingCrudComponent />
                </TabPanel>
            </Box>
        </div>
    )
}

function a11yProps(index: number) {
    return {
        id: `vertical-tab-${index}`,
        'aria-controls': `vertical-tabpanel-${index}`,
    };
}

interface TabPanelProps {
    children?: React.ReactNode;
    index: number;
    value: number;
}

function TabPanel(props: TabPanelProps) {
    const { children, value, index, ...other } = props;

    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`vertical-tabpanel-${index}`}
            aria-labelledby={`vertical-tab-${index}`}
            {...other}
        >
            {value === index && (
                <Box sx={{ p: 3 }}>
                    <Typography>{children}</Typography>
                </Box>
            )}
        </div>
    );
}