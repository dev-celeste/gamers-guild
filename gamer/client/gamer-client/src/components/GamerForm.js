import { useContext, useEffect, useState } from "react";
import AuthContext from "../context/AuthContext";
import { useParams, useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";

function GamerForm() {

    const auth = useContext(AuthContext);
    const GAMER_PROFILE_BLANK = {
        gamerId: auth.userGamer.gamerId,
        appUserId: auth.user.appUserId,
        genderType:"PREFER_NOT_TO_SAY",
        gamerTag:"",
        birthDate:"",
        bio:"",
        games:[],
    }
    const [gamer, setGamer] = useState(GAMER_PROFILE_BLANK);

    const [errors, setErrors] = useState([]);

    const navigate = useNavigate();
    const { id } = useParams();
    const url = "http://localhost:8080/gamer";

    // get Gamer Profile information if userGamer exists
    useEffect( () => {
        if (id) {
            // i have to use == here instead of === because id is a seen as a string
            if (auth.userGamer.gamerId == id) {

                fetch(`${url}/${id}`)
                .then(response => {
                    if (response.status === 200) {
                        return response.json();
                    } else {
                        return Promise.reject(`Unexpected status code: ${response.status}`);
                    }
                })
                .then( data => {
                    setGamer(data);
                    console.log(data);
                })
                .catch(console.log);
            } else {
                console.log("You're trying to edit a profile that's not yours!");
                navigate("/error", {state: {message: "You're trying to edit a profile that isn't yours!"}});
            }
        }
    }, []);

    const handleChange = (event) => {
        const nextGamer = {...gamer};
        nextGamer[event.target.name] = event.target.value;
        setGamer(nextGamer);
        // for bugtesting
        console.log(nextGamer);
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        console.log("submitting...");
        const newGamer = {...gamer};
        if(id) {
            const init = {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify(newGamer)
            };

            fetch(`${url}/${id}`, init)
            .then(response => {
                if(response.status === 204) {
                    return null;
                } else if(response.status === 400){
                    return response.json();
                }
                else{
                    return Promise.reject(`Unexpected status code: ${response.status}`);
                }
            })
            .then(data =>{
                console.log(data);
                if(!data){
                    // TEMP url, make sure after successfully updating the user just goes to see their own profile again
                    // but maybe we should have a confirmation message first?!
                    auth.attachGamer(auth.user.appUserId);
                    navigate("/success", {state: {message: `Congrats ${auth.user.username} / ${gamer.gamerTag}, you successfully updated your profile!`}});
                }else{
                    setErrors(data);
                }
            })
            .catch(console.log);
        } else {
            // if no id, it's a new acc!
            const init = {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify(newGamer)
            };
            fetch(`${url}`, init)
            .then(response => {
                if(response.status === 201 || response.status === 400){
                    return response.json();
                }else{
                    return Promise.reject(`Unexpected status code: ${response.status}`);
                }
            })
            .then(data =>{
                if(data.gamerId){
                    console.log(data);
                    console.log(data.gamerId);         
                    // TEMP url, make sure after successfully creating, the user goes to see their own profile
                    auth.attachGamer(auth.user.appUserId);
                    navigate("/success", {state: {message: `Congrats ${auth.user.username}, You created your profile with the gamertag ${gamer.gamerTag}!`}});
                } else{
                    setErrors(data);
                }
            })
            .catch(console.log)

        }
    };

    return(
        <main className="container">
            <section id="gamerProfileForm">
                <h2>{id > 0 ? `Edit ${gamer.gamerTag}'s Profile` : "Create your Profile!"}</h2>
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
                        <label htmlFor="gamerTag">Gamer Tag:</label>
                        <input id="gamerTag" 
                        name="gamerTag" 
                        type="text" 
                        className="form-control" 
                        value={gamer.gamerTag}
                        onChange={handleChange}/>
                    </fieldset>

                    <fieldset className="form-group">
                        <label htmlFor="birthDate">Birthday:</label>
                        <input id="birthDate" 
                        name="birthDate" 
                        type="date" 
                        className="form-control"
                        value={gamer.birthDate}
                        onChange={handleChange}/>
                    </fieldset>

                    <fieldset className="form-group">
                        <label htmlFor="genderType">Gender:</label>
                        <select 
                        name="genderType" 
                        id="genderType" 
                        placeholder="Select gender..."
                        className="form-control"
                        value={gamer.genderType}
                        onChange={handleChange}>
                            <option value="MALE">Male</option>
                            <option value="FEMALE">Female</option>
                            <option value="NONBINARY">Nonbinary</option>
                            <option value="OTHER">Other</option>
                            <option value="PREFER_NOT_TO_SAY">Prefer not to say</option>
                        </select>
                    </fieldset>

                    <fieldset className="form-group">
                        <label htmlFor="bio">About me:</label>
                        <input id="bio" 
                        name="bio" 
                        type="textarea" 
                        className="form-control"
                        value={gamer.bio}
                        onChange={handleChange}/>
                    </fieldset>


                    <div className="mt-4">
                        <button className="btn btn-success mr-2" type="submit">
                        {id > 0 ? "Save Changes" : "Create Profile!"}

                        </button>
                        <Link className="btn btn-warning" type="button" to={"/profile"}>
                            Cancel
                        </Link>
                    </div>
                </form>
            </section>
        </main>
    );
}

export default GamerForm;