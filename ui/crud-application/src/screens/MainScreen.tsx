import {AppBar, Container, Toolbar} from "@mui/material";
import "./MainScreen.scss"
import TabbedContentPane from "../components/TabbedContentPane";

export default function MainScreen() {
    return (
        <div className="main-screen">
            <AppBar position="static" className="menu-bar">
                <Container maxWidth="xl">
                    <Toolbar disableGutters>
                        <img src="/assets/img/redhat_logo.png" alt="logo" className="logo"/>
                        <h1 className="title">RH Movie Platform</h1>
                    </Toolbar>
                </Container>
            </AppBar>
            <div className="screen-content">
                <TabbedContentPane/>
            </div>
        </div>
    )
}