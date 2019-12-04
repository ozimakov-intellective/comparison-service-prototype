import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import {Box} from '@material-ui/core';
import {Button} from "@material-ui/core";
import Paper from '@material-ui/core/Paper';
import CircularProgress from "@material-ui/core/CircularProgress";
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import {
    BrowserRouter as Router,
    Switch,
    Route
} from "react-router-dom";
import axios from 'axios'

function AppRouter(props) {
    return (
        <Router>
            <Switch>
                <Route path="/logon">
                    <Login error={window.location.search.indexOf("error=1") !== -1} />
                </Route>
                <Route path="/">
                    <App/>
                </Route>
            </Switch>
        </Router>
    );
}

class App extends React.Component {

    constructor(props, context) {
        super(props, context);
        this.state = {
            currentPage: 'upload',
            result: []
        };
    }

    render() {
        switch (this.state.currentPage) {
            case 'upload':
                return <UploadPage handler={(files) => this.compare(files)}/>;
            case 'result':
                return <ResultPage handler={() => this.reset()} data={this.state.result}/>;
            case 'wait':
                return <WaitingPage/>;
            case 'error':
                return <ErrorPage handler={() => this.reset()} error={this.state.error}/>;
            default:
                return <Box>Undefined state: {this.state.currentPage}</Box>;
        }
    }

    compare(files) {
        this.setState({currentPage: "wait"});
        let form_data = new FormData();
        Array.from(files).forEach(f => form_data.append("files", f));

        let url = '/api/compare';
        axios.post(url, form_data, {
            headers: {
                'content-type': 'multipart/form-data'
            }
        })
            .then(res => {
                this.setState({
                    result: res.data,
                    currentPage: "result"
                });
            }).catch(err => this.setState({
                currentPage: "error",
                error: err.message
            }));
    }

    reset() {
        this.setState({currentPage: "upload"});
    }

}

class UploadPage extends React.Component {

    constructor(props, context) {
        super(props, context);
        this.handler = props.handler;
        this.handleSubmit = this.handleSubmit.bind(this);
        this.filesInput = React.createRef();
    }

    handleSubmit(event) {
        event.preventDefault();
        this.handler(this.filesInput.current.files);
    }

    render() {
        return (
            <Box display="flex" justifyContent="center">
                <Paper>
                    <form onSubmit={this.handleSubmit}>
                        <Box m={5}>
                            <input multiple type="file" ref={this.filesInput}/>
                        </Box>
                        <Box m={5}>
                            <Button variant="contained" color="primary" type="submit">
                                Compare documents
                            </Button>
                        </Box>
                    </form>
                </Paper>
            </Box>
        );
    }

}

function ResultPage(props) {
    return (
        <Box display="flex" justifyContent="center" m={5}>
            <Paper>
                <Box m={5}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell>Document File Name</TableCell>
                                <TableCell align="right">Group Id</TableCell>
                                <TableCell align="right">Similarity Ratio</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {props.data.map(row => (
                                <TableRow key={row.id}>
                                    <TableCell component="th" scope="row">
                                        {row.id}
                                    </TableCell>
                                    <TableCell align="right">{row.groupId}</TableCell>
                                    <TableCell align="right">{row.ratio}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </Box>
                <Box m={5}>
                    <Button variant="contained" color="primary"
                            onClick={props.handler}>
                        Go Back
                    </Button>
                </Box>
            </Paper>
        </Box>
    );
}

function WaitingPage(props) {
    return (
        <Box display="flex" justifyContent="center">
            <Box>
                <CircularProgress/>
            </Box>
        </Box>
    );
}

function ErrorPage(props) {
    return (
        <Box display="flex" justifyContent="center">
            <Paper>
                <Box m={5}>
                    Error occured: {props.error}
                </Box>
                <Box m={5}>
                    <Button variant="contained" color="secondary"
                        onClick={props.handler}>Go back</Button>
                </Box>
            </Paper>
        </Box>
    )
}

function Login(props) {
    return (
        <Box display="flex" justifyContent="center">
            <Paper>
                <form action="login" method="post">
                    <Box m={5}>
                            <table>
                                <tbody>
                                    <tr>
                                        <td>Login</td>
                                        <td><input name="username"/></td>
                                    </tr>
                                    <tr>
                                        <td>Password</td>
                                        <td><input name="password" type="password"/></td>
                                    </tr>
                                </tbody>
                            </table>
                    </Box>
                    <Box m={5}>
                        <div style={{color: 'red'}}>{props.error ? "Incorrect credentials. Try again." : ""}</div>
                    </Box>
                    <Box m={5}>
                        <Button variant="contained" color="primary" type="submit">Log in</Button>
                    </Box>
                </form>
            </Paper>
        </Box>
    );
}


// ========================================

ReactDOM.render(
    <AppRouter/>,
    document.getElementById('root')
);
