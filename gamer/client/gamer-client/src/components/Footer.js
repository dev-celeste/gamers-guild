import { Link } from 'react-router-dom';
function Footer() {
    return(<>
        <footer>
            <ul>
                <li><Link to={'/'}>Social Media</Link></li>
                <li><Link to={'/contact'}>Contact Us</Link></li>
                <li><Link to={'/faq'}>FAQ</Link></li>
            </ul>
        </footer>
    </>);
}

export default Footer;