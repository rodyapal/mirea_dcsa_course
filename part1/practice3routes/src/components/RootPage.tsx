import Header from "./Header";
import React from "react";
import Typography from "@mui/material/Typography";


export default function RootPage() {
    return (
        <div>
            <Header title={"Root page"}/>
            <div>
                <a href={"/one"}>
                    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                        Page one
                    </Typography>
                </a>
                <a href={"/two"}>
                    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                        Page two
                    </Typography>
                </a>
            </div>
        </div>
    )
}