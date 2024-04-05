/**
 * Name: Charuni Liyanage, Simon Gordon, Olasubomi Badiru
 * Class: CMPT 305 AS01
 * Instructor: Dr. Indratmo Indratmo
 */
package com.example.cmpt305project.HelperClasses;

import java.util.ArrayList;
import java.util.List;


public class ConvexHull {
    // Utility function to find orientation of ordered triplet (p, q, r).
    // Returns the following values:
    // 0 : Collinear Cpoints
    // 1 : Clockwise Cpoints
    // 2 : Counterclockwise Cpoints
    private static int orientation(CPoint p, CPoint q, CPoint r) {
        double val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
        if (val == 0)
            return 0; // Collinear
        return (val > 0) ? 1 : 2; // Clockwise or Counterclockwise
    }

    // Function to find the convex hull of a set of n Cpoints
    public static List<CPoint> convexHull(List<CPoint> Cpoints) {
        int n = Cpoints.size();
        if (n < 3)
            return null; // Convex hull not possible

        // Find the leftmost Cpoint
        int l = 0;
        for (int i = 1; i < n; i++) {
            if (Cpoints.get(i).x < Cpoints.get(l).x)
                l = i;
        }

        List<CPoint> hull = new ArrayList<>();

        int p = l, q;
        do {
            hull.add(Cpoints.get(p));

            q = (p + 1) % n;
            for (int i = 0; i < n; i++) {
                // If i is more counterclockwise than current q, then update q
                if (orientation(Cpoints.get(p), Cpoints.get(i), Cpoints.get(q)) == 2)
                    q = i;
            }

            p = q;

        } while (p != l); // While we don't come back to the start Cpoint

        return hull;
    }
}