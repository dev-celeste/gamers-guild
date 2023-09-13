import { Link, useLocation } from "react-router-dom";

function Success(){
    const location = useLocation();

    return(
    <main>
        <h2>Success!</h2>
        <p>{ location.state ? `${location.state.message}` : ""}</p>
    </main>
    )
}

export default Success;