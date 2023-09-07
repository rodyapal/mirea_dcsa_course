import React from 'react';
import './index.css'
import ReactDOM from 'react-dom/client';
import {
    createBrowserRouter,
    RouterProvider,
} from "react-router-dom";
import Page from "./components/Page";
import RootPage from "./components/RootPage";

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);

const router = createBrowserRouter([
    {
        path: "/",
        element: <RootPage/>
    },
    {
        path: "/one",
        element: <Page title = {"Page one"} cardButtonText={"Submit"} cardTitle={"Title one"}/>
    },
    {
        path: "/two",
        element: <Page title = {"Page two"} cardButtonText={"Read more"} cardTitle={"Title two"}/>
    }
])
root.render(
  <React.StrictMode>
    <RouterProvider router={router}/>
  </React.StrictMode>
);