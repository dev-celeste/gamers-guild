import React, {useState} from "react"

const Searchbar = ({setResults}) => {
    const [input, setInput] = useState("")
    const url = 'http://localhost:8080/game'
    const fetchData = (value) => {
        fetch(url)
        .then((response) => response.json()
        .then(json => {
            const results = json.filter((game) => {
                return value && game.gameTitle.toLowerCase().includes(value);
            });
            setResults(results);
        }))
    }
    const handleChange = (value) => {
        setInput(value)
        fetchData(value)
        if(value === "") {
            // window.location.reload(false);
            window.location = "/community";
        }
    }
    return(
        <div>
            <input placeholder="Type to search:" value={input} onChange={(e) => handleChange(e.target.value)}/>
        </div>

    );
}

export default Searchbar;