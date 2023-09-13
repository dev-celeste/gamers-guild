// import React, {useState} from 'react'


// const Searchbar = () => {
//     const [searchInput, setSearchInput] = useState("");
//     const games = [
//         { name: "League of Legends", title: "League of Legends" },
//         { name: "Yakuza 0", title: "Yakuza 0" },
//         { name: "Ghost Trick: Phantom Detective", title: "Ghost Trick: Phantom Detective" },
//         { name: "Animal Crossing: New Horizons", title: "Animal Crossing: New Horizons" },
//         { name: "Sims 4", title: "Sims 4" }
//     ];

//     const handleChange = (e) => {
//         e.preventDefault();
//         setSearchInput(e.target.value);
//     };

//     if (searchInput.length > 0) {
//         games.filter((game) => {
//         return game.name.match(searchInput);
//     });
//     }

//     return (<div>

//         <input
//         type="search"
//         placeholder="Search here"
//         onChange={handleChange}
//         value={searchInput} />

//         <table>
//         <tr>
//             <th>Game</th>
//         </tr>

//         {
//         games.filter(game => {
//             if (searchInput === '') {
//                 return game;
//             } else if (game.title.toLowerCase().includes(searchInput.toLowerCase())) {
//                 return game;
//             }
//         }).map((game, index) => (

//         <div key={index}>
//         <tr>
//             <td>{game.name}</td>
//         </tr>
//         </div>

//         ))}
//         </table>

//     </div>);


// }

import React, { useState } from 'react'
import TextField from '@material-ui/core/TextField';
import Autocomplete from '@material-ui/lab/Autocomplete';
 
const Searchbar = () => {
 
  const [myOptions, setMyOptions] = useState([])
 
  const getDataFromAPI = () => {
    console.log("Options Fetched from API")
 
    fetch('http://dummy.restapiexample.com/api/v1/employees').then((response) => {
      return response.json()
    }).then((res) => {
      console.log(res.data)
      for (let i = 0; i < res.data.length; i++) {
        myOptions.push(res.data[i].employee_name)
      }
      setMyOptions(myOptions)
    })
  }
 
  return (
    <div style={{ marginLeft: '40%', marginTop: '60px' }}>
      <h3>Greetings from GeeksforGeeks!</h3>
      <Autocomplete
        style={{ width: 500 }}
        freeSolo
        autoComplete
        autoHighlight
        options={myOptions}
        renderInput={(params) => (
          <TextField {...params}
            onChange={getDataFromAPI}
            variant="outlined"
            label="Search Box"
          />
        )}
      />
    </div>
  );
}



export default Searchbar;