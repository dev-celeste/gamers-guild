import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

function SignUp() {
    const navigate = useNavigate();
    const [credentials, setCredentials] = useState({
        username: "",
        password: ""
    })
    const [errors, setErrors] = useState([]);
    const url = "http://localhost:8080";

    const handleChange = (event) => {
        const nextCredentials = {...credentials};
        nextCredentials[event.target.name] = event.target.value;
        setCredentials(nextCredentials);
        // for bugtesting
        console.log(nextCredentials);
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        console.log("yay");
        const init = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify(credentials),
        }

        fetch(`${url}/create_account`, init)
        .then(response => {
            if (response.status === 201 ) {
                console.log(response.json());
                navigate("/success", {state: {message: `You are now signed up as ${credentials.username}. Please login!`}});
            } else if (response.status === 400) {
                return response.json();
            } else { 
                return Promise.reject(`${response.status}: Unexpected error code. Account creation failed.`);
            } 
        })
        .then(data =>{
                setErrors(data);
        })
        .catch(console.log)
    }
    

    return (
        <main className="container">
            <section id="loginContainer">
                <h2 className="loginH2">Sign Up For Gamer's Guild!</h2>
                {errors.length > 0 && (
                    <div className="alert alert-danger">
                        <p>The following errors were found:</p>
                        <ul>
                            {errors.map(error => 
                            <li key={error}>{error}</li>
                            )}
                        </ul>
                    </div>
                )}

                <form onSubmit={handleSubmit}>
                <fieldset className="form-group">
                        <label htmlFor="username">Username:</label>
                        <input id="username" 
                        name="username" 
                        type="text" 
                        className="form-control" 
                        onChange={handleChange}/>
                    </fieldset>
                    <fieldset className="form-group">
                        <label htmlFor="password">Password:</label>
                        <input id="password" 
                        name="password" 
                        type="password" 
                        className="form-control"
                        onChange={handleChange}/>
                    </fieldset>
                    <div className="mt-4">
                        <button className="btn btn-success mr-2 button loginButton" type="submit">
                            Create Account
                        </button>
                        <Link to={"/login"}><button className="btn btn-warning button loginButton" type="button">
                            Cancel
                        </button></Link>
                    </div>
                </form>
            </section>
        </main>
    );
}

export default SignUp;