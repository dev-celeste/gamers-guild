import Searchbar from "./search/Searchbar";
import SpecificPosts from "./search/SpecificPosts";
import AllPosts from "./search/AllPosts";
import { useState } from "react";
import { Link } from 'react-router-dom';


function Community() {
    const [results, setResults] = useState([]);
    const [active, setActive] = useState(false);
    const [currentGameId, setCurrentGameId] = useState([]);

    function hideResults() {
        let x = document.getElementById("results")
        x.style.display = "none";
    }

    function clearSearch() {
        let x = document.getElementById("results")
        x.style.display = "none";
    }


    return(<>
        <section className="community">
            <div className="searchBarContainer">
                <Searchbar setResults={setResults}/>
            </div>
            <div id="results">
                {
                    results.map((result, gameId) => {
                        return <div key={gameId} onClick={() => {{setCurrentGameId(result.gameId)}; {setActive(true)}; {hideResults()}; {clearSearch()}}}>{result.gameTitle}</div> 
                    })
                }{console.log(currentGameId)}
            </div>
            <div className="communityBody">        
            <Link to={"/make-post"}><button className="button postButton">Make a Post</button></Link>

            <p className="communityP">In dapibus ac magna nec ornare. Nam justo ante, faucibus quis sapien non, congue eleifend diam. Proin eget justo ac tortor aliquam finibus. In sapien elit, vestibulum a odio non, ultricies laoreet nisi. Pellentesque mattis lorem vel tristique mollis. Suspendisse ac bibendum mi. Maecenas accumsan lobortis elit ut condimentum. Sed feugiat eu purus a luctus. Fusce ac lobortis justo, eget finibus nisi. Duis dictum est vel bibendum pharetra. Nullam sollicitudin, lacus a imperdiet sollicitudin, tortor sem scelerisque nunc, ut blandit lorem nunc nec nisl. Vivamus ornare hendrerit mi at imperdiet. Phasellus sem sapien, vestibulum vel porta et, dapibus sit amet arcu. Phasellus sed ipsum eu ex interdum ultrices.</p>

            <div className="post">
                {active === false && <AllPosts/>}
                {active === true && <SpecificPosts currentGame={currentGameId}/>}
            </div>
            </div>
            
        </section>
    </>);
}

export default Community;