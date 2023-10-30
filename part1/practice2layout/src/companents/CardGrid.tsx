import React from 'react'
import {Grid} from "@mui/material"
import styles from './CardGrid.module.css'
import Card from './Card'

export default function CardGrid() {
    return (
        <div className={styles.GridMargin}>
            <Grid container rowSpacing={1} columnSpacing={{ xs: 1, sm: 2, md: 3 }}>
                <Grid item xs={6}>
                    <Card />
                </Grid>
                <Grid item xs={6}>
                    <Card />
                </Grid>
                <Grid item xs={6}>
                    <Card />
                </Grid>
                <Grid item xs={6}>
                    <Card />
                </Grid>
            </Grid>
        </div>
    )
}